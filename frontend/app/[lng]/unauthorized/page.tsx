import React from "react";
import { Metadata } from "next";
import UnAuthorizedComponent from "./UnauthorizedComponent";

export const metadata: Metadata = {
  title: "Unauthorized",
  description: "Unauthorized",
};

export default function UnauthorizedPage({}) {
  return (
    <UnAuthorizedComponent/>
  );
}
