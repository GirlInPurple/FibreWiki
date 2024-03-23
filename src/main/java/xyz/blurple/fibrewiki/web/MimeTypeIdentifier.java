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

import java.util.HashMap;
import java.util.Map;

public class MimeTypeIdentifier {
    private Map<String, String> mimeTypes = new HashMap<>();

    public MimeTypeIdentifier() {
        mimeTypes.put("ai", "application/postscript");
        mimeTypes.put("aif", "audio/x-aiff");
        mimeTypes.put("aifc", "audio/x-aiff");
        mimeTypes.put("aiff", "audio/x-aiff");
        mimeTypes.put("asc", "text/plain");
        mimeTypes.put("au", "audio/basic");
        mimeTypes.put("avi", "video/x-msvideo");
        mimeTypes.put("bcpio", "application/x-bcpio");
        mimeTypes.put("bmp", "image/bmp");
        mimeTypes.put("cdf", "application/x-netcdf");
        mimeTypes.put("csh", "application/x-csh");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("doc", "application/msword");
        mimeTypes.put("dot", "application/msword");
        mimeTypes.put("dvi", "application/x-dvi");
        mimeTypes.put("eml", "message/rfc822");
        mimeTypes.put("eps", "application/postscript");
        mimeTypes.put("etx", "text/x-setext");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("gtar", "application/x-gtar");
        mimeTypes.put("gz", "application/x-gzip");
        mimeTypes.put("hdf", "application/x-hdf");
        mimeTypes.put("htm", "text/html");
        mimeTypes.put("html", "text/html");
        mimeTypes.put("ief", "image/ief");
        mimeTypes.put("jpe", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("js", "text/javascript"); // "application/javascript" is deprecated
        mimeTypes.put("json", "application/json");
        mimeTypes.put("ksh", "text/plain");
        mimeTypes.put("latex", "application/x-latex");
        mimeTypes.put("m1v", "video/mpeg");
        mimeTypes.put("m3u", "audio/x-mpegurl");
        mimeTypes.put("man", "application/x-troff-man");
        mimeTypes.put("me", "application/x-troff-me");
        mimeTypes.put("mht", "message/rfc822");
        mimeTypes.put("mhtml", "message/rfc822");
        mimeTypes.put("mif", "application/x-mif");
        mimeTypes.put("mov", "video/quicktime");
        mimeTypes.put("movie", "video/x-sgi-movie");
        mimeTypes.put("mp2", "audio/mpeg");
        mimeTypes.put("mp3", "audio/mpeg");
        mimeTypes.put("mp4", "video/mp4");
        mimeTypes.put("mpa", "video/mpeg");
        mimeTypes.put("mpe", "video/mpeg");
        mimeTypes.put("mpeg", "video/mpeg");
        mimeTypes.put("mpg", "video/mpeg");
        mimeTypes.put("ms", "application/x-troff-ms");
        mimeTypes.put("nc", "application/x-netcdf");
        mimeTypes.put("nws", "message/rfc822");
        mimeTypes.put("oda", "application/oda");
        mimeTypes.put("p12", "application/x-pkcs12");
        mimeTypes.put("p7c", "application/pkcs7-mime");
        mimeTypes.put("pbm", "image/x-portable-bitmap");
        mimeTypes.put("pdf", "application/pdf");
        mimeTypes.put("pfx", "application/x-pkcs12");
        mimeTypes.put("pgm", "image/x-portable-graymap");
        mimeTypes.put("pl", "text/plain");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("pnm", "image/x-portable-anymap");
        mimeTypes.put("pot", "application/mspowerpoint");
        mimeTypes.put("ppa", "application/vnd.ms-powerpoint");
        mimeTypes.put("ppm", "image/x-portable-pixmap");
        mimeTypes.put("pps", "application/mspowerpoint");
        mimeTypes.put("ppt", "application/mspowerpoint");
        mimeTypes.put("ps", "application/postscript");
        mimeTypes.put("pwz", "application/vnd.ms-powerpoint");
        mimeTypes.put("py", "text/x-python");
        mimeTypes.put("pyc", "application/x-python-code");
        mimeTypes.put("pyo", "application/x-python-code");
        mimeTypes.put("qt", "video/quicktime");
        mimeTypes.put("ra", "audio/x-pn-realaudio");
        mimeTypes.put("ram", "application/x-pn-realaudio");
        mimeTypes.put("ras", "image/x-cmu-raster");
        mimeTypes.put("rdf", "application/xml");
        mimeTypes.put("rgb", "image/x-rgb");
        mimeTypes.put("roff", "application/x-troff");
        mimeTypes.put("rtx", "text/richtext");
        mimeTypes.put("sgm", "text/x-sgml");
        mimeTypes.put("sgml", "text/x-sgml");
        mimeTypes.put("sh", "application/x-sh");
        mimeTypes.put("shar", "application/x-shar");
        mimeTypes.put("silo", "model/mesh");
        mimeTypes.put("sit", "application/x-stuffit");
        mimeTypes.put("skd", "application/x-koan");
        mimeTypes.put("skm", "application/x-koan");
        mimeTypes.put("skp", "application/x-koan");
        mimeTypes.put("skt", "application/x-koan");
        mimeTypes.put("smi", "application/smil");
        mimeTypes.put("smil", "application/smil");
        mimeTypes.put("snd", "audio/basic");
        mimeTypes.put("spl", "application/x-futuresplash");
        mimeTypes.put("src", "application/x-wais-source");
        mimeTypes.put("sv4cpio", "application/x-sv4cpio");
        mimeTypes.put("sv4crc", "application/x-sv4crc");
        mimeTypes.put("svg", "image/svg+xml");
        mimeTypes.put("swf", "application/x-shockwave-flash");
        mimeTypes.put("t", "application/x-troff");
        mimeTypes.put("tar", "application/x-tar");
        mimeTypes.put("tcl", "application/x-tcl");
        mimeTypes.put("tex", "application/x-tex");
        mimeTypes.put("texi", "application/x-texinfo");
        mimeTypes.put("texinfo", "application/x-texinfo");
        mimeTypes.put("tif", "image/tiff");
        mimeTypes.put("tiff", "image/tiff");
        mimeTypes.put("tr", "application/x-troff");
        mimeTypes.put("tsv", "text/tab-separated-values");
        mimeTypes.put("txt", "text/plain");
        mimeTypes.put("ustar", "application/x-ustar");
        mimeTypes.put("vcd", "application/x-cdlink");
        mimeTypes.put("vrml", "model/vrml");
        mimeTypes.put("vxml", "application/voicexml+xml");
        mimeTypes.put("wav", "audio/x-wav");
        mimeTypes.put("wbmp", "image/vnd.wap.wbmp");
    }

    /**
     * If a corresponding MimeType exists, return that type.
     * Otherwise, start a download.
     * @param input FileName
     * @return MimeType
     * */
    public String compare(String input) {
        return mimeTypes.getOrDefault(input, "application/octet-stream");
    }
}
