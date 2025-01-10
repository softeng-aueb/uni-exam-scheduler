"use client";

import React, { createContext, useEffect, useState } from "react";
// import { readProjects } from "../lib/dbOperations/collections/Projects";

const commonDataDefault = {};

const Context = createContext(commonDataDefault);

function ContextProvider({ children }: { children: any }) {
  const [projects, setProjects] = useState<any>(null);

  // Local Storage: setting & getting data
  useEffect(() => {
    (async () => {
      // get project
      try {
        const [projects] = await Promise.all([
          [], // readProjects(),
        ]);

        setProjects(projects);
      } catch (e: any) {
        console.log(`Error: ${e}`);
      }
    })();
  }, []);

  return (
    <Context.Provider
      value={{
        projects,
        setProjects,
      }}
    >
      {children}
    </Context.Provider>
  );
}

export { ContextProvider, Context };
