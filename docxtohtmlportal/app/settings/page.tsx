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


import { Button } from "@/components/ui/button";

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
import { Separator } from "@/components/ui/separator";


export default function FileConversionProfileSelector() {
  //defaultProfile
  const [createNewProfile, setCreateNewProfile] = useState(false);
  const [currentProfile, setCurrentProfile] = useState("default");
  return (
    <div className="container my-24">
      <div className="text-[32px] font-[700] mb-4">Settings</div>

      <Separator className="my-8" />
      <div className="grid gap-2">
        <h1 className="text-[20px]">Conversion profile</h1>
        <p className="text-[16px]">
          Select the default conversion profile to be used or create new.
        </p>
      </div>
      <div className="grid gap-6 mt-12">
        <div className="flex items-center space-x-4">
          <Label htmlFor="airplane-mode" className="text-[16px]">
            Create New
          </Label>
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
      <Button>Save</Button>
    </div>
  );
}
