/*
 * Copyright 2016 Sebastian Gil.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.create.mybatis.repository.query;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * MyBatis specific implementation of {@link QueryMethod}.
 */
public class MyBatisQueryMethod extends QueryMethod {

    private final Method method;

    /**
     * Creates a {@link MyBatisQueryMethod}.
     *
     * @param method             must not be {@literal null}
     * @param metadata           must not be {@literal null}
     */
    public MyBatisQueryMethod(final Method method, final RepositoryMetadata metadata) {
        super(method, metadata);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
