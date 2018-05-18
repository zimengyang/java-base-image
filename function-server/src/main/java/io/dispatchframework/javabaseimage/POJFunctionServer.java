///////////////////////////////////////////////////////////////////////
// Copyright (c) 2018 VMware, Inc. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0
///////////////////////////////////////////////////////////////////////
package io.dispatchframework.javabaseimage;

import io.undertow.Handlers;
import io.undertow.Undertow;

/**
 * A Server implementation that responsible for running Dispatch functions
 * with no dependency on Spring.
 */
public class POJFunctionServer implements Server {
    private Undertow undertow;

    public POJFunctionServer(String packageName, String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        undertow = Undertow.builder().addHttpListener(8080, "0.0.0.0").setHandler(Handlers.path()
                .addPrefixPath("/", new HttpHandlers.ExecFunction(packageName, className)).addExactPath("/healthz", new HttpHandlers.Healthz()))
                .build();
    }

    public void start() {
        this.undertow.start();
    }

    public void stop() {
        this.undertow.stop();
    }
}