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

package com.github.kafeindev.commands.bukkit;

import com.github.kafeindev.commands.common.command.completion.Completion;
import com.github.kafeindev.commands.common.command.completion.CompletionProvider;
import com.github.kafeindev.commands.common.component.SenderComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public final class BukkitCompletionProvider extends CompletionProvider {
    @Override
    public void initialize() {
        // Player
        register(new Completion("@players") {
            @Override
            public List<String> complete(@Nullable SenderComponent sender) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(HumanEntity::getName)
                        .collect(Collectors.toList());
            }
        });

        // World
        register(new Completion("@worlds") {
            @Override
            public List<String> complete(@Nullable SenderComponent sender) {
                return Bukkit.getServer().getWorlds().stream()
                        .map(world -> world.getName())
                        .collect(Collectors.toList());
            }
        });
    }
}
