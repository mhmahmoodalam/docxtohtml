"use client";
import React from "react";
import { useRouter } from "next/navigation";
import {
  File,
  Plus,
  View,
} from "lucide-react";

import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  TooltipProvider,
} from "@/components/ui/tooltip";

import { getRunningJobsForState, JobStates, IJobsData } from "../_uidata";
import { FunctionComponent, useEffect, useState } from "react";

import { DataTableSkeleton } from "../../components/appcomponents/Loader";
import { PreviewConvertedHtml } from "@/components/appcomponents";

type SelectedTabJobsListProps = {
  selectedTab: JobStates;
};

const JobsDataTable: FunctionComponent<SelectedTabJobsListProps> = ({
  selectedTab,
}) => {
  const [jobsData, setJobsData] = useState<Array<IJobsData>>([]);
  useEffect(() => {
    setTimeout(() => {
      const jobs = getRunningJobsForState(selectedTab);
      setJobsData(jobs);
    }, 2000);
  }, []);

  if (jobsData.length < 1 ) {
    return <DataTableSkeleton />;
  }

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Job Id</TableHead>
          <TableHead>File Name</TableHead>
          <TableHead className="hidden md:table-cell">Date</TableHead>
          <TableHead className="hidden sm:table-cell">Status</TableHead>
          <TableHead className="hidden md:table-cell">Progress</TableHead>
          <TableHead className="text-right">Preview</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {jobsData.map((value, key) => {
          return (
            <TableRow  key={`job_data_row${value.jobId}`}>
              <TableCell>
                {value.jobId}
              </TableCell>
              <TableCell>
                {value.name}
              </TableCell>
              <TableCell className="hidden md:table-cell">
                {value.date}
              </TableCell>
              <TableCell className="hidden sm:table-cell">
                <Badge className="text-xs" variant="secondary">
                  {value.state}
                </Badge>
              </TableCell>

              <TableCell className="hidden md:table-cell">
                <Progress
                className="h-2"
                  value={value.progress}
                  aria-label={`${value.progress}%`}
                />
              </TableCell>
              <TableCell className="text-right">
                <PreviewConvertedHtml name={value.name} previewId={value.previewId} />
              </TableCell>
            </TableRow>
          );
        })}
      </TableBody>
    </Table>
  );
};

const SelectedTabJobsList: FunctionComponent<SelectedTabJobsListProps> = ({
  selectedTab,
}) => {
  return (
    <TabsContent value={selectedTab}>
      <Card>
        <CardHeader className="px-7">
          <CardTitle>{selectedTab} Jobs</CardTitle>
          <CardDescription>
            Recent {selectedTab.toLowerCase()} jobs.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <JobsDataTable selectedTab={selectedTab} />
        </CardContent>
      </Card>
    </TabsContent>
  );
};

export default function Dashboard() {
  const defaultSelctedTab = JobStates.Processing;
  const router = useRouter();
  return (
    <TooltipProvider>
      <div className="flex flex-row min-h-screen w-full justify-center bg-muted/40">
        <div className="flex w-full sm:gap-4 sm:py-4 sm:pl-14 items-start">
          <main className="flex-1 w-full justify-center items-start gap-4 p-4 sm:px-6 sm:py-0 md:gap-8 ">
            <div className="flex flex-col items-start m-32 mt-16out gap-4 md:gap-8 min-w-4xl">
              <div className="text-[32px] font-[700] mb-4">Jobs View</div>
              <Tabs defaultValue={defaultSelctedTab} className="w-full">
                <div className="flex items-center">
                  <TabsList>
                    {Object.values(JobStates).map((value, key) => {
                      return (
                        <TabsTrigger
                          value={value}
                          key={`jobstate_tab_${value}`}
                        >
                          {value}
                        </TabsTrigger>
                      );
                    })}
                  </TabsList>
                  <div className="ml-auto flex items-center gap-2">
                    <Button
                      size="sm"
                      variant="outline"
                      className="h-8 gap-1 text-sm"
                    >
                      <File className="h-3.5 w-3.5" />
                      <span className="sr-only sm:not-sr-only">Export</span>
                    </Button>
                    <Button
                      size="sm"
                      variant="default"
                      className="h-8 gap-2 p-4 text-sm"
                      onClick={() => router.push("/start-new")}
                    >
                      <Plus className="h-3.5 w-3.5" />
                      <span className="sr-only sm:not-sr-only">Start New</span>
                    </Button>
                  </div>
                </div>
                {Object.values(JobStates).map((value, key) => {
                  return (
                    <SelectedTabJobsList
                      selectedTab={value}
                      key={`selected_tab_jobs_list_${key}`}
                    />
                  );
                })}
              </Tabs>
            </div>
          </main>
        </div>
      </div>
    </TooltipProvider>
  );
}
