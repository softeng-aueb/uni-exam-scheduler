import type { Metadata } from "next";
import { dir } from "i18next";

// Utils
import { languages } from "@/app/i18n/settings";
import AppProvider from "@/app/providers";

export async function generateStaticParams() {
  return languages.map((lng: string) => ({ lng }));
}

export const metadata: Metadata = {
  title: "ESAP",
  description: "ESAP",
  applicationName: "ESAP",
  creator: "ESAP",
  icons: {},
};

export default async function RootLayout({ children, params }: { children: React.ReactNode, params: any }) {

  return (
    <AppProvider>
      <html lang={params.lng} dir={dir(params.lng)}>
      <body>
      {children}
      </body>
      </html>
    </AppProvider>
  );
}
