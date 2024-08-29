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

import { Button } from "@/components/ui/button";
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

import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

import { Label } from "@/components/ui/label";
import { Switch } from "@/components/ui/switch";
import { Info } from "lucide-react";
import { useState } from "react";
import { Separator } from "../ui/separator";

type FileConversionProfileSelectorProps = {
  name: string;
  id: Number;
  selectedProfile: string;
};

export function FileConversionProfileSelector({
  name,
  id,
  selectedProfile,
}: FileConversionProfileSelectorProps) {
  //defaultProfile
  const [createNewProfile, setCreateNewProfile] = useState(false);
  const [currentProfile, setCurrentProfile] = useState(selectedProfile);
  return (
    <Sheet>
      <SheetTrigger asChild>
        <Button aria-haspopup="true" variant="ghost">
          {selectedProfile}
          <Info className="mx-2 h-4 w-4" />
        </Button>
      </SheetTrigger>
      <SheetContent>
        <SheetHeader>
          <SheetTitle>Conversion profile :</SheetTitle>
          <SheetDescription>
            File Name : {name}
            <br />
            <br />
            Select the conversion profile to be used for the conversion of this
            file or create new.
          </SheetDescription>
        </SheetHeader>
        <Separator className="mt-8" />
        <div className="grid gap-8 py-4 mt-4">
          <div className="flex items-center justify-between space-x-2">
            <Label htmlFor="airplane-mode">Create New</Label>
            <Switch
              id="create-new"
              checked={createNewProfile}
              onCheckedChange={setCreateNewProfile}
            />
          </div>
          {createNewProfile && <>new profile creation</>}
          {!createNewProfile && (
            <Select
              onValueChange={setCurrentProfile}
              defaultValue={currentProfile}
            >
              <SelectTrigger className="w-[180px]">
                <SelectValue placeholder="Select a profile" />
              </SelectTrigger>
              <SelectContent>
                <SelectGroup>
                  <SelectLabel>Profiles</SelectLabel>
                  <SelectItem value="default">Default</SelectItem>
                  <SelectItem value="embedded">Embedded Comments</SelectItem>
                </SelectGroup>
              </SelectContent>
            </Select>
          )}
          <Separator className="my-4" />
        </div>
        <SheetFooter>
          <SheetClose asChild>
            <Button type="button" size={"sm"}>
              Close
            </Button>
          </SheetClose>
        </SheetFooter>
      </SheetContent>
    </Sheet>
  );
}
