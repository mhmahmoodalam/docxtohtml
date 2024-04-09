"use client";

import { DataTableSkeleton } from "@/components/appcomponents/Loader";
import {
  Card,
  CardContent,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import {
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
  Table,
} from "@/components/ui/table";
import { FunctionComponent, useState, useEffect } from "react";

import listFiles
 from "./FilesList";

import { DirectoryInputForm, DirectoryInputFormSchema}
  from "@/components/appcomponents/DirectoryInputButton";
import { z } from "zod";
import { Button } from "@/components/ui/button";
import { FileConversionProfileSelector } from "@/components/appcomponents/FileConversionSelector";

type FilesListTableProps = {
  filesList: ConvertDocxToHtml[];
  updateConversionProfile: Function | null;
  updateShouldProcess: Function | null;
};

interface ConvertDocxToHtml {
  serial: Number;
  fileName: string;
  location: string;
  conversionProfile: string;
  shouldProcess: Boolean;
}

const FilesListTable: FunctionComponent<FilesListTableProps> = ({ filesList }) => {
  useEffect(() => {
    
  }, []);

  if (filesList.length < 1) {
    return <DataTableSkeleton />;
  }

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead className="hidden md:table-cell">Serial</TableHead>
          <TableHead>File Name</TableHead>
          <TableHead>Profile</TableHead>
          <TableHead className="hidden md:table-cell">Location</TableHead>
          <TableHead className="hidden sm:table-cell">Process</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {filesList.map((value, key) => {
          return (
            <TableRow key={`filesList_row${key}`}>
              <TableCell>{`${value.serial}`}</TableCell>
              <TableCell>{value.fileName}</TableCell>
              <TableCell>
                <FileConversionProfileSelector
                  name={value.fileName}
                  id={value.serial}
                  selectedProfile={value.conversionProfile}
                />
              </TableCell>
              <TableCell>{value.location}</TableCell>
              <TableCell>{`${value.shouldProcess}`}</TableCell>
            </TableRow>
          );
        })}
      </TableBody>
    </Table>
  );
};

export default function StartNewJob() {
  const [selectedFiles, setSelectedFiles] = useState<ConvertDocxToHtml[]>([]);
  const handleFileSelect = async ({
    directoryPath,
  }: z.infer<typeof DirectoryInputFormSchema>) => {    
    const files = directoryPath;
    if (files) {
      listFiles(files)
      .then(x => {
        var filesTobeConverted: ConvertDocxToHtml[] = [];
        x.forEach((file, index) => {
          filesTobeConverted.push({
            serial: index + 1,
            fileName: file.name,
            shouldProcess: true,
            conversionProfile: "default",
            location: file.path,
          });
        });
        setSelectedFiles(filesTobeConverted);
      });
    }

  };

  return (
    <div className="container my-24">
      <div className="flex flex-col space-y-6">
        <div className="text-[32px] font-[700] mb-4">
          Start new Docx To Html Job
        </div>
        <DirectoryInputForm onSubmit={handleFileSelect} />
        <Separator className="my-4" />

        {selectedFiles.length > 0 && (
          <>
            <Card>
              <CardContent>
                <div className="flex items-center gap-4 mt-4">
                  <h1 className="flex-1 shrink-0 whitespace-nowrap text-xl font-semibold tracking-tight sm:grow-0">
                    Files Summary
                  </h1>
                  <div className="hidden items-center gap-2 md:ml-auto md:flex">
                    <div>
                      Total <span className="font-semibold">{selectedFiles.length}</span> files
                    </div>
                  </div>
                </div>

                <Separator className="my-4" />
                <FilesListTable
                  filesList={selectedFiles}
                  updateConversionProfile={null}
                  updateShouldProcess={null}
                />
              </CardContent>
            </Card>
            <div className="flex flex-col items-end gap-4 mt-4">
              <Button className=""> Convert Docx to Html</Button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
