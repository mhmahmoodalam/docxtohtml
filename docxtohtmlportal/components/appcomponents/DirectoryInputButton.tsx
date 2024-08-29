/*
# Copyright 2024 Muhammed Mahmood Alam
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 */

"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";

type InputFormProps = {
  onSubmit : Function
}

export const DirectoryInputFormSchema = z.object({
  directoryPath: z.string().min(2, {
    message: "directory must be at least 2 characters.",
  }),
});

export function DirectoryInputForm(props: InputFormProps) {
  const form = useForm<z.infer<typeof DirectoryInputFormSchema>>({
    resolver: zodResolver(DirectoryInputFormSchema),
    defaultValues: {
      directoryPath: "",
    },
  });

  function onSubmit(data: z.infer<typeof DirectoryInputFormSchema>) {
    props.onSubmit(data);
  }

  return (
    <Form {...form}>
      <form
        onClick={form.handleSubmit(onSubmit)}
        className="flex flex-row w-full max-w-2xl items-center space-x-2"
      >
        <p>Location:</p>
        <FormField
          control={form.control}
          name="directoryPath"
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Input placeholder="D:\test\doxcfiles" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="button">Load files</Button>
      </form>
    </Form>
  );
}
