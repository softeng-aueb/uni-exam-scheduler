import React from "react";
import { Metadata } from "next";
import SupervisorsComponent from "./SupervisorsComponent";
import { readSupervisors } from "@/app/lib/dbOperations";

export const metadata: Metadata = {
  title: "Supervisors",
  description: "Supervisors",
};

async function fetchData() {
  const supervisors = await readSupervisors();
  return supervisors;
}

export default async function SupervisorsPage({ params: { lng } }: any) {
  const supervisors = await fetchData();
  return (
    <SupervisorsComponent lng={lng} pageData={supervisors}/>
  );
}
