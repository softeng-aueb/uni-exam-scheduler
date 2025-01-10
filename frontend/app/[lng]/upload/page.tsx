import React from "react";
import { Metadata } from "next";

import UploadComponent from "./ui/UploadComponent";

export const metadata: Metadata = {
  title: "Uploads",
  description: "Uploads",
};

export default function UploadPage({ params: { lng } }: any) {
  return <UploadComponent lng={lng}/>;
}
