import { Skeleton } from "@/components/ui/skeleton";
import { TableHeader, TableRow, TableHead, TableBody, TableCell } from "@/components/ui/table";
import { Table, Badge } from "lucide-react";

export default function Loader() {
  return (
    <div className="w-full flex justify-center items-center min-h-20">
      <div
        className="inline-block h-12 w-12 animate-spin rounded-full border-4 border-solid border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]"
        role="status"
      >
        <span className="!absolute !-m-px !h-px !w-px !overflow-hidden !whitespace-nowrap !border-0 !p-0 ![clip:rect(0,0,0,0)]">
          Loading...
        </span>
      </div>
    </div>
  );
}

export function DataTableSkeleton() {
  return (
    <div className="flex flex-col h-96 w-full space-y-3">
      <div className="flex flex-col">
        <div className="flex flex-row space-x-3">
          <Skeleton className="h-12 w-1/6" />
          <Skeleton className="h-12 w-1/6" />
          <Skeleton className="h-12 w-1/6" />
          <Skeleton className="h-12 w-1/6" />
          <Skeleton className="h-12 w-1/6" />
          <Skeleton className="h-12 w-1/6" />
        </div>
        <hr className="h-2 mt-2"/>
      </div>
      <div className="flex flex-col space-y-3">
        {[1, 2, 3, 5, 6, 7, 8, 9, 10].map((value, key) => {
          return (
            <div key={`job_data_row${value}`}>
              <div className="flex flex-row space-x-3">
                <Skeleton className="h-10 w-1/6" />
                <Skeleton className="h-10 w-1/6" />
                <Skeleton className="h-10 w-1/6" />
                <Skeleton className="h-10 w-1/6" />
                <Skeleton className="h-10 w-1/6" />
                <Skeleton className="h-10 w-1/6" />
              </div>
              <hr className="h-2 mt-2" />
            </div>
          );
        })}
      </div>
    </div>
  );
}
