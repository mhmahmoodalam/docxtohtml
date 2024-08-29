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

import { IJobsData } from "@/app/_uidata";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetDescription,
  SheetFooter,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { View } from "lucide-react";

type PreviewConvertedHtmlProps = {
  name: String, 
  previewId: Number
}

export function PreviewConvertedHtml({ name, previewId }: PreviewConvertedHtmlProps) {
  return (
    <Sheet>
      <SheetTrigger asChild>
        <Button aria-haspopup="true" size="icon" variant="ghost">
          <View className="h-4 w-4" />
          <span className="sr-only">Preview</span>
        </Button>
      </SheetTrigger>
      <SheetContent>
        <SheetHeader>
          <SheetTitle>FileName : {name}</SheetTitle>
          <SheetDescription>
            Preview the Converted file. Not All features can be used in the
            html.
          </SheetDescription>
        </SheetHeader>
        <div className="grid gap-4 py-4"></div>
        <SheetFooter>
          <SheetClose asChild>
            <Button type="submit">Close</Button>
          </SheetClose>
        </SheetFooter>
      </SheetContent>
    </Sheet>
  );
}
