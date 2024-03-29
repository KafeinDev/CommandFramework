/*
 * MIT License
 *
 * Copyright (c) 2023 Kafein's CommandFramework
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

package com.github.kafeindev.commands.common.command.convert;

import com.github.kafeindev.commands.common.command.Command;
import com.github.kafeindev.commands.common.command.CommandBuilder;
import com.github.kafeindev.commands.common.command.abstraction.DefaultCommand;
import com.github.kafeindev.commands.common.command.annotation.*;
import com.github.kafeindev.commands.common.command.base.BaseCommand;
import com.github.kafeindev.commands.common.reflect.ReflectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class DefaultCommandConverter implements CommandConverter {
    @Override
    public @NotNull Command convert(@NotNull BaseCommand baseCommand) {
        Class<?> clazz = baseCommand.getClass();

        Annotation[] annotations = clazz.getAnnotations();
        if (annotations.length == 0) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " has no annotations!");
        }

        Method noArgsMethod = ReflectionUtils.getMethodAnnotatedWith(clazz, NoArgsCommand.class);
        return CommandBuilder.newBuilder(baseCommand)
                .executor(noArgsMethod)
                .parents(clazz.getAnnotation(CommandParent.class), ReflectionUtils.getAnnotation(noArgsMethod, CommandParent.class))
                .aliases(clazz.getAnnotation(CommandAlias.class), ReflectionUtils.getAnnotation(noArgsMethod, CommandAlias.class))
                .usage(clazz.getAnnotation(CommandUsage.class), ReflectionUtils.getAnnotation(noArgsMethod, CommandUsage.class))
                .completions(clazz.getAnnotation(CommandCompletion.class), ReflectionUtils.getAnnotation(noArgsMethod, CommandCompletion.class))
                .description(clazz.getAnnotation(CommandDescription.class), ReflectionUtils.getAnnotation(noArgsMethod, CommandDescription.class))
                .permission(clazz.getAnnotation(CommandPermission.class), ReflectionUtils.getAnnotation(noArgsMethod, CommandPermission.class))
                .build(DefaultCommand.class);
    }

    @Override
    public @Nullable Command convert(@NotNull BaseCommand baseCommand, @NotNull Method method) {
        Annotation[] annotations = method.getAnnotations();
        if (annotations.length == 0) {
            return null;
        }

        //is a child command?
        if (method.getAnnotation(Subcommand.class) == null) {
            return null;
        }

        return CommandBuilder.newBuilder(baseCommand)
                .executor(method)
                .aliases(method.getAnnotation(Subcommand.class))
                .usage(method.getAnnotation(CommandUsage.class))
                .completions(method.getAnnotation(CommandCompletion.class))
                .description(method.getAnnotation(CommandDescription.class))
                .permission(method.getAnnotation(CommandPermission.class))
                .build(DefaultCommand.class);
    }
}
