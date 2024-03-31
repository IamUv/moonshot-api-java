package com.iamuv.moonshot.api.infrastructure.util;

import java.util.UUID;

public class BoundaryGenerator {

    public static String generateBoundary() {
        // 使用UUID生成一个唯一的字符串作为boundary
        return "---------------------------" + UUID.randomUUID();
    }

}
