/*
 *      Copyright [2020] [xiaozhennan1995@gmail.com]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwan.support.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

public class StdoutRedirect {
    private final static Logger logger = LoggerFactory.getLogger(StdoutRedirect.class);

    public static void redirectSystemOutAndErrToLog() {
        PrintStream printStreamForOut = createLoggingWrapper(System.out, false);
        PrintStream printStreamForErr = createLoggingWrapper(System.out, true);
        System.setOut(printStreamForOut);
        System.setErr(printStreamForErr);
    }

    public static PrintStream createLoggingWrapper(final PrintStream printStream, final boolean isErr) {
        return new PrintStream(printStream) {
            @Override
            public void print(final String string) {
                if (!isErr) {
                    logger.debug(string);
                } else {
                    logger.error(string);
                }
            }

            @Override
            public void print(boolean b) {
                if (!isErr) {
                    logger.debug(String.valueOf(b));
                } else {
                    logger.error(String.valueOf(b));
                }
            }

            @Override
            public void print(char c) {
                if (!isErr) {
                    logger.debug(String.valueOf(c));
                } else {
                    logger.error(String.valueOf(c));
                }
            }

            @Override
            public void print(int i) {
                if (!isErr) {
                    logger.debug(String.valueOf(i));
                } else {
                    logger.error(String.valueOf(i));
                }
            }

            @Override
            public void print(long l) {
                if (!isErr) {
                    logger.debug(String.valueOf(l));
                } else {
                    logger.error(String.valueOf(l));
                }
            }

            @Override
            public void print(float f) {
                if (!isErr) {
                    logger.debug(String.valueOf(f));
                } else {
                    logger.error(String.valueOf(f));
                }
            }

            @Override
            public void print(double d) {
                if (!isErr) {
                    logger.debug(String.valueOf(d));
                } else {
                    logger.error(String.valueOf(d));
                }
            }

            @Override
            public void print(char[] x) {
                if (!isErr) {
                    logger.debug(x == null ? null : new String(x));
                } else {
                    logger.error(x == null ? null : new String(x));
                }
            }

            @Override
            public void print(Object obj) {
                if (!isErr) {
                    logger.debug(String.valueOf(obj));
                } else {
                    logger.error(String.valueOf(obj));
                }
            }
        };
    }
}  