package com.jushi.oss.handler;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import com.jushi.api.pojo.Result;
import com.jushi.oss.pojo.consts.OssConst;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * @author 80795
 * @date 2019/7/17 21:06
 */
@Component
public class OssHandler {


    @Autowired
    private OSS oss;


    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * 文件上传
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> upload(ServerRequest request) {
        Mono<MultiValueMap<String, Part>> multiValueMapMono = request.body(BodyExtractors.toMultipartData());
        return multiValueMapMono.flatMap(multiValueMap -> {
            List<Part> editor = multiValueMap.get("file");
            FilePart filePart = (FilePart) editor.get(0);
            FormFieldPart formFieldPart = (FormFieldPart) multiValueMap.getFirst("path");
            String prefix = formFieldPart.value();
            if (prefix.startsWith("/") || prefix.startsWith("\\")) {
                prefix = prefix.substring(1);
            }
            try {
                Path filePath = Files.createTempFile("oss", filePart.filename());
                File file = filePath.toFile();
                filePart.transferTo(file);
                InputStream inputStream = new FileInputStream(file);

                String fileName = prefix + UUID.randomUUID() + filePart.filename();
                PutObjectResult putObjectResult = oss.putObject(OssConst.SPRING_OSS_BUCKET_NAME,
                        fileName, inputStream);
                inputStream.close();
                return ServerResponse.ok().body(Mono.just(Result.success("上传成功", OssConst.PATH_PREFIX + fileName)), Result.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ServerResponse.ok().body(Mono.just(Result.error("文件上传失败")), Result.class);
        });
    }

    /**
     * 文件上传 gridfs
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> gridfsUpload(ServerRequest request) {
        Mono<MultiValueMap<String, Part>> multiValueMapMono = request.body(BodyExtractors.toMultipartData());
        return multiValueMapMono.flatMap(multiValueMap -> {
            FilePart filePart = (FilePart) multiValueMap.getFirst("file");
            FormFieldPart path = (FormFieldPart) multiValueMap.getFirst("path");

            String fileName = path.value() + filePart.filename();

            File file = new File(filePart.filename());
            filePart.transferTo(file);
            try (InputStream inputStream = new FileInputStream(file)) {
                ObjectId objectId = gridFsTemplate.store(inputStream, fileName);
                return ServerResponse.ok().body(Mono.just(Result.success("上传文件成功", objectId.toString())), Result.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ServerResponse.ok().body(Mono.just(Result.error("上传文件失败")), Result.class);
        });
    }
}
