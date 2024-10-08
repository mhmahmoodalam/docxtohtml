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

import Link from "next/link";

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  CircleUser,
  Menu,
  Combine,
  Search,
  LayoutDashboard,
  Wrench,
  Settings,
} from "lucide-react";
import { usePathname } from "next/navigation";
import { ModeToggle } from ".";

export function NavigationBar() {
  const pathname = usePathname();

  return (
    <header className="sticky top-0 flex h-16 items-center gap-4 border-b bg-background px-4 md:px-6 md:w-full">
      <nav className="hidden flex-col gap-6 text-lg font-medium md:flex md:flex-row md:items-center md:gap-6 md:text-sm md:w-full lg:gap-6">
        <Link
          href="/"
          className="flex flex-row mr-10 items-center gap-2 text-lg font-semibold md:text-base"
        >
          <Combine className="h-6 w-6" />
          DocxToHtml
        </Link>
        <Link
          href="/dashboard"
          className={`${
            pathname.match("/dashboard")
              ? "text-foreground"
              : "text-muted-foreground"
          } transition-colors hover:text-foreground flex flex-row gap-2`}
        >
          <LayoutDashboard className="h-6 w-6" />
          Dashboard
        </Link>
        <Link
          href="/running-jobs"
          className={`${
            pathname.match("/running-jobs")
              ? "text-foreground"
              : "text-muted-foreground"
          } transition-colors hover:text-foreground flex flex-row gap-2`}
        >
          <Wrench className="h-6 w-6" />
          Running Jobs
        </Link>
        <Link
          href="/settings"
          className={`${
            pathname.match("/settings")
              ? "text-foreground"
              : "text-muted-foreground"
          } transition-colors hover:text-foreground flex flex-row gap-2`}
        >
          <Settings className="h-6 w-6" />
          Settings
        </Link>
      </nav>
      <Sheet>
        <SheetTrigger asChild>
          <Button variant="outline" size="icon" className="shrink-0 md:hidden">
            <Menu className="h-5 w-5" />
            <span className="sr-only">Toggle navigation menu</span>
          </Button>
        </SheetTrigger>
        <SheetContent side="left">
          <nav className="grid gap-6 text-lg font-medium">
            <Link
              href="/"
              className="flex flew-row items-center gap-2 text-lg font-semibold"
            >
              <Combine className="h-6 w-6" />
              DocxToHtml
            </Link>
            <Link
              href="/dashboard"
              className={`${
                pathname.match("/dashboard") ? "" : "text-muted-foreground"
              } hover:text-foreground`}
            >
              Dashboard
            </Link>
            <Link
              href="/running-jobs"
              className={`${
                pathname.match("/running-jobs") ? "" : "text-muted-foreground"
              } hover:text-foreground`}
            >
              Running Jobs
            </Link>
            <Link
              href="/settings"
              className={`${
                pathname.match("/settings") ? "" : "text-muted-foreground"
              } hover:text-foreground`}
            >
              Settings
            </Link>
          </nav>
        </SheetContent>
      </Sheet>
      <div className="flex w-full items-center gap-4 md:ml-auto md:gap-2 lg:gap-4">
        <form className="ml-auto flex-1 sm:flex-initial">
          <div className="relative">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
            <Input
              type="search"
              placeholder="Search products..."
              className="pl-8 sm:w-[300px] md:w-[200px] lg:w-[300px]"
            />
          </div>
        </form>
        
        <ModeToggle />
      </div>
    </header>
  );
}
