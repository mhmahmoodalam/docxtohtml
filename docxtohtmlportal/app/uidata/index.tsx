import RunningJobStates from "./RunningJobsData"


enum JobStates {
  Processing = "Processing",
  Completed = "Completed",
  Failed = "Failed",
}

export interface IJobsData {
  jobId: number;
  state: string;
  name: string;
  date: string;
  progress: number;
  preview: boolean;
  previewId: number;
}

const getRunningJobsForState = (state: JobStates): Array<IJobsData> => {  
  return RunningJobStates.filter((x) => x.state === state);
};

export { getRunningJobsForState, JobStates };