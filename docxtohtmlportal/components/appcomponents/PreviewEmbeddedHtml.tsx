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
