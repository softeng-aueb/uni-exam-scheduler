import React from "react";
import { Metadata } from "next";

import NotFoundComponent from "./ui/NotFound";

export const metadata: Metadata = {
  title: "Not Found",
  description: "Not Found",
};

export default function NotFound({ params }: any) {
  return (
    <NotFoundComponent lng={params?.lng}/>
  );
}
