package com.iamuv.moonshot.api.domain.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * 文件 领域模型
 * <p>
 * 上传成功后返回值：{"id":"co3t989kqq4ua4e78urg","object":"file","bytes":5426,"created_at":1711789217,"filename":"我是一个文件.txt","purpose":"file-extract","status":"ok","status_details":""}
 * <p>
 * 文件基础信息返回值：{"id":"co3t989kqq4ua4e78urg","object":"file","bytes":5426,"created_at":1711789217,"filename":"我是一个文件.txt","purpose":"file-extract","status":"ok","status_details":""}
 * <p>
 * 文件列表返回值：{"object":"list","data":[{"id":"co3t989kqq4ua4e78urg","object":"file","bytes":5426,"created_at":1711789217,"filename":"我是一个文件.txt","purpose":"file-extract","status":"ok","status_details":""}]}
 * <p>
 * 文件抽取信息返回值：{"content":"文件内容1\n\n文件内容2","file_type":"text/plain","filename":"我是一个文件.txt","title":"","type":"file"}
 * <p>
 * 文件删除返回值：{"deleted":true,"id":"co3t989kqq4ua4e78urg","object":"file"}
 */
public class Files extends Api {

    protected static final String filesUrl = baseUrl + slash + version + slash + "files";
    protected static final String filesUrlWithSlash = filesUrl + slash;
    protected static final String filesContentUrl = filesUrlWithSlash + "%s" + slash + "content";


    public String getFilesUrl() {
        return filesUrl;
    }

    public String getFilesContentUrl(String id) {
        return String.format(filesContentUrl, id);
    }

    public static String getFilesUrlWithSlash() {
        return filesUrlWithSlash;
    }

    public Map<String, String> getUploadHeaders(String token, String boundary) {
        Map<String, String>
                headers = getDefaultJsonHeaders(token);
        headers.put(contentType, contentTypeMultipartFormData + "; boundary=" + boundary);
        return headers;
    }

    public String getUploadRequestBody(Path filePath, String boundary) throws IOException {
        return "--" + boundary + System.lineSeparator() +
                "Content-Disposition: form-data; name=\"file\"; filename=\"" + filePath.getFileName() + "\"" + System.lineSeparator() +
                "Content-Type: " + java.nio.file.Files.probeContentType(filePath) + System.lineSeparator() +
                System.lineSeparator() +
                java.nio.file.Files.readString(filePath) + System.lineSeparator() +
                "--" + boundary + "--" + System.lineSeparator();

    }

    public Map<String, String> getDefaultHeaders(String token) {
        return getDefaultJsonHeaders(token);
    }

    public String getFilesDeleteUrl(String id) {
        return getFilesUrlWithSlash() + id;
    }

    public String getFilesGetUrl(String id) {
        return getFilesUrlWithSlash() + id;
    }
}
