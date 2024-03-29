/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Kafein's CommandFramework
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.kafeindev.commands.common.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public interface SenderComponent {
    @Nullable UUID getUniqueId();

    @Nullable String getName();

    void sendMessage(@NotNull String message);

    boolean hasPermission(@NotNull String permission);

    boolean isOnline();

    boolean isConsole();

    boolean isPlayer();

    default void isPlayer(@NotNull Runnable action1, @NotNull Runnable action2) {
        if (isPlayer()) {
            action1.run();
        } else {
            action2.run();
        }
    }

    default <T> T isPlayer(@NotNull Supplier<T> action1, @NotNull Supplier<T> action2) {
        return isPlayer() ? action1.get() : action2.get();
    }
}
