/*
 * Copyright (c) 2024 Jonas_Jones, magistermaks, TheColorBlurple
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package xyz.blurple.fibrewiki.web;

import xyz.blurple.fibrewiki.FibreWiki;
import xyz.blurple.fibrewiki.config.ModConfigs;
import xyz.blurple.fibrewiki.util.VerboseLogger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.StringTokenizer;

import static xyz.blurple.fibrewiki.account.AccountHandler.stageNewCode;
import static xyz.blurple.fibrewiki.web.ServerHandler.isServerActive;

public class HttpServer implements Runnable {

    public static boolean editingEnabled = true;
    public static void setEditingEnabled(boolean b) {editingEnabled = b;}
    static Path WEB_ROOT;
    static final String DEFAULT_FILE = ModConfigs.WEB_FILE_ROOT;
    static final String FILE_NOT_FOUND = ModConfigs.WEB_FILE_404;
    static final String METHOD_NOT_SUPPORTED = ModConfigs.WEB_FILE_NOSUPPORT;
    static final int PORT = ModConfigs.WEB_PORT;
    private static final byte[] NOT_IMPLEMENTED = "HTTP/1.1 405 Method Not Allowed\r\n".getBytes(StandardCharsets.UTF_8);
    private static final byte[] OK = "HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8);
    private static final byte[] NOT_FOUND = "HTTP/1.1 404 Not Found\r\n".getBytes(StandardCharsets.UTF_8);
    private static final byte[] HEADERS = String.join(
            "\r\n",
            "Server: Java HTTP Server from SSaurel : 1.0",
            "X-Frame-Options: DENY",
            "X-Content-Type-Options: nosniff",
            "" // trailing CRLF
    ).getBytes(StandardCharsets.UTF_8);
    private static final byte[] CRLF = new byte[]{0x0D, 0x0A};

    // Client Connection via Socket Class
    private final Socket connect;
    private final MimeTypeIdentifier mimetypeidentifier = new MimeTypeIdentifier();

    static {
        try {
            WEB_ROOT = Path.of(ModConfigs.WEB_ROOT).toRealPath(LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            WEB_ROOT = Path.of(ModConfigs.WEB_ROOT);
        }
    }

    public HttpServer(Socket c) {
        connect = c;
    }

    public static void main() {
        try {
            try (ServerSocket serverConnect = new ServerSocket(PORT)) {
                FibreWiki.LOGGER.info("Server started.");
                FibreWiki.LOGGER.info("Listening for connections on port : " + PORT);

                // we listen until user halts server execution
                while (isServerActive) {
                    HttpServer myServer = new HttpServer(serverConnect.accept());

                    VerboseLogger.info("Connection opened. (" + Instant.now() + ")");

                    // create dedicated thread to manage the client connection
                    Thread thread = new Thread(myServer);
                    thread.setName("FibreWiki-worker");
                    thread.start();

                }
            }

        } catch (IOException e) {
            VerboseLogger.error("Server Connection error : " + e.getMessage());
        }
    }

    @Override
    public void run() {
        if (isServerActive) {
            // we manage our particular client connection
            BufferedReader in = null;
            PrintWriter out = null;
            BufferedOutputStream dataOut = null;
            String fileRequested = null;
            String hexCode = null;

            try {
                // we read characters from the client via input stream on the socket
                in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                // we get character output stream to client (for headers)
                out = new PrintWriter(connect.getOutputStream());
                // get binary output stream to client (for requested data)
                dataOut = new BufferedOutputStream(connect.getOutputStream());
                // get first line of the request from the client
                String input = in.readLine();
                // we parse the request with a string tokenizer
                StringTokenizer parse = new StringTokenizer(input);
                // we get the HTTP method of the client
                String method = parse.nextToken().toUpperCase();
                // we get file requested
                fileRequested = parse.nextToken().toLowerCase();

                // we support only certain methods, here they are checked
                if (!method.equals("GET") && !method.equals("HEAD")) {
                    VerboseLogger.info("501 Not Implemented : " + method + " method.");

                    // we return the not supported file to the client
                    Path file = WEB_ROOT.resolve(METHOD_NOT_SUPPORTED);
                    long fileLength = Files.size(file);
                    String contentMimeType = "text/html";
                    //read content to return to client
                    byte[] fileData = readFileData(file);

                    // we send HTTP Headers with data to client
                    dataOut.write(NOT_IMPLEMENTED);
                    dataOut.write(HEADERS); //hopefully enough credits
                    dataOut.write("Date: %s\r\n".formatted(Instant.now()).getBytes(StandardCharsets.UTF_8));
                    dataOut.write("Content-Type: %s\r\n".formatted(contentMimeType).getBytes(StandardCharsets.UTF_8));
                    dataOut.write("Content-Length: %s\r\n".formatted(fileLength).getBytes(StandardCharsets.UTF_8));
                    dataOut.write(CRLF); // blank line between headers and content, very important !
                    // file
                    dataOut.write(fileData, 0, fileData.length);
                    dataOut.flush();
                } else {
                    // GET or HEAD method
                    if (fileRequested.endsWith("/")) {
                        fileRequested += DEFAULT_FILE;
                    }
                    if (fileRequested.startsWith("/")) {
                        fileRequested = fileRequested.substring(1);
                    }

                    if (fileRequested.equals("code")) {
                        hexCode = stageNewCode(connect.getInetAddress());
                        fileRequested = "code.json";
                    }

                    Path file = WEB_ROOT.resolve(fileRequested).toRealPath(LinkOption.NOFOLLOW_LINKS);
                    if (!file.startsWith(WEB_ROOT)) {
                        VerboseLogger.warn("Access to file outside root: " + file);
                        throw new NoSuchFileException(fileRequested);
                    }
                    int fileLength = (int) Files.size(file);
                    int fileExtensionStartIndex = fileRequested.lastIndexOf(".") + 1;
                    String contentType;
                    if (fileExtensionStartIndex > 0) {
                        contentType = mimetypeidentifier.compare(fileRequested.substring(fileExtensionStartIndex));
                    } else {
                        contentType = "text/plain";
                    }

                    byte[] fileData = readFileData(file);

                    // send HTTP Headers
                    dataOut.write(OK);
                    dataOut.write(HEADERS);
                    dataOut.write("Date: %s\r\n".formatted(Instant.now()).getBytes(StandardCharsets.UTF_8));
                    dataOut.write("Content-Type: %s\r\n".formatted(contentType).getBytes(StandardCharsets.UTF_8));
                    dataOut.write("Content-Length: %s\r\n".formatted(fileLength).getBytes(StandardCharsets.UTF_8));
                    dataOut.write(CRLF); // blank line between headers and content, very important !
                    if (method.equals("GET")) { // GET method so we return content
                        dataOut.write(fileData, 0, fileLength);
                        dataOut.flush();
                    }

                    VerboseLogger.info("File " + fileRequested + " of type " + contentType + " returned");

                }

            } catch (NoSuchFileException e) {
                try {
                    assert out != null;
                    assert dataOut != null;
                    fileNotFound(out, dataOut, fileRequested);
                } catch (IOException ioe) {
                    VerboseLogger.error("Error with file not found exception : " + ioe.getMessage());
                }

            } catch (IOException ioe) {
                VerboseLogger.error("Server error : " + ioe);
            } finally {
                try {
                    in.close();
                    out.close();
                    assert dataOut != null;
                    dataOut.close();
                    connect.close(); // we close socket connection
                } catch (Exception e) {
                    VerboseLogger.error("Error closing stream : " + e.getMessage());
                }

                VerboseLogger.info("Connection closed.");
            }

        }
    }

    private byte[] readFileData(Path file) throws IOException {
        return Files.readAllBytes(file);
    }

    private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException {
        Path file = WEB_ROOT.resolve(FILE_NOT_FOUND);
        int fileLength = (int) Files.size(file);
        String contentType = "text/html";
        byte[] fileData = readFileData(file);

        dataOut.write(NOT_FOUND);
        dataOut.write(HEADERS);
        dataOut.write("Date: %s\r\n".formatted(Instant.now()).getBytes(StandardCharsets.UTF_8));
        dataOut.write("Content-Type: %s\r\n".formatted(contentType).getBytes(StandardCharsets.UTF_8));
        dataOut.write("Content-Length: %s\r\n".formatted(fileLength).getBytes(StandardCharsets.UTF_8));
        dataOut.write(CRLF); // blank line between headers and content, very important !
        out.flush(); // flush character output stream buffer

        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();

        VerboseLogger.error("File " + fileRequested + " not found");
    }
}
