import React from "react";
import { Metadata } from "next";
import LanguagesComponent from "./LanguagesComponent";

export const metadata: Metadata = {
  title: "Languages",
  description: "Languages",
};

export default function LanguagesPage({ params: { lng } }: any) {
  return (
    <LanguagesComponent lng={lng}/>
  );
}
