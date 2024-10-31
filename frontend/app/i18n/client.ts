/* eslint-disable react-hooks/rules-of-hooks */

"use client";

import { useEffect, useState } from "react";
import i18next from "i18next";
import { initReactI18next, useTranslation as useTranslationOrg } from "react-i18next";
import { useCookies } from "react-cookie";
import resourcesToBackend from "i18next-resources-to-backend";
import LanguageDetector from "i18next-browser-languagedetector";
import { cookieName, getOptions, languages } from "./settings";

const runsOnServerSide = typeof window === "undefined";

i18next
    .use(initReactI18next)
    .use(LanguageDetector)
    .use(resourcesToBackend((language: any, namespace: any) => import(`./locales/${language}/translation.json`)))
    .init({
      ...getOptions(),
      lng: undefined, // let detect the language on client side
      detection: {
        order: ["path", "htmlTag", "cookie", "navigator"],
      },
      preload: runsOnServerSide ? languages : [],
    });

export function useTranslation(lng: any, ns?: any, options?: any) {
  const [cookies, setCookie] = useCookies([cookieName]);

  const ret = useTranslationOrg(ns, options);

  const { i18n } = ret;

  if (runsOnServerSide && lng && i18n.resolvedLanguage !== lng) {
    void i18n.changeLanguage(lng);
  } else {
    const [activeLng, setActiveLng] = useState(i18n.resolvedLanguage);

    useEffect(() => {
      if (activeLng === i18n.resolvedLanguage) return;
      setActiveLng(i18n.resolvedLanguage);
    }, [activeLng, i18n.resolvedLanguage]);

    useEffect(() => {
      if (!lng || i18n.resolvedLanguage === lng) return;
      void i18n.changeLanguage(lng);
    }, [lng, i18n]);

    useEffect(() => {
      if (cookies.i18next === lng) return;
      setCookie(cookieName, lng ?? i18n.resolvedLanguage, {
        // maxAge: 24 * 60 * 60,
        // httpOnly: true,
        path: "/",
        sameSite: "strict",
      });
    }, [lng, cookies.i18next]);
  }

  return ret;
}
