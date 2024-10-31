"use client";

import React, { useEffect, useState } from "react";
import { useSession } from "next-auth/react";

// MUI
import { Container } from "@mui/material";

// Components
import Breadcrumb from "@/app/ui/Breadcrumb";
import Layout from "@/app/ui/layout";
import PageCardHeader from "@/app/ui/PageCardHeader";
import SpinnerWithPromise from "@/app/ui/Spinner/SpinnerPromise";
import { DataGridComponent } from "@/app/ui/DataGrid";
// Components
import CreateEditModalConfigLanguages from "@/app/ui/CRUDCreateEditModals/Languages";
import LanguagesCol from "@/app/ui/CRUDColumns/Admin/Languages/Cols";

// DB Operations
import {
  deleteLanguage as deleteDoc,
  readLanguages as readDocs,
  searchLanguages as searchDocs,
  updateLanguage as updateDoc,
} from "@/app/lib/dbOperations";

// Helpers
import { onSearch } from "@/app/ui/Common/cruds";
import { useTranslation } from "@/app/i18n/client";

const entityNamePlural = "Languages";
const entityNameSingular = "Language";
const searchSpinnerArea = "search-languages-area";
const spinnerArea = "read-languages";

export default function LanguagesComponent({ lng }: any) {
  const { data: session }: any = useSession();
  const { t } = useTranslation(lng);

  const [addEditModalOpen, setAddEditModalOpen] = useState(false);
  const [data, setData] = useState<any>([]);
  const [dataAll, setDataAll] = useState<any>([]);
  const [search, setSearch] = useState<string>("");
  const [selectedDoc, setSelectedDoc] = useState<any>({});

  useEffect(() => {
    async function init() {
      const { data }: { data: any } = await readDocs();

      setDataAll(data);
      setData(data);
    }

    (async () => {
      await init();
    })();
  }, []);

  const breadcrumbList = [{
    label: "Home",
    url: "/",
  }, {
    label: "Admin",
    url: "/admin",
  }, {
    label: t("languages.label_many"),
    url: "/admin/languages",
  }];

  const handleModalClose = () => setAddEditModalOpen(false);
  const handleModalOpen = () => setAddEditModalOpen(true);

  const onSearchTextChange = (event: any) => {
    setSearch(event.target.value);
    if (event.target.value === "") {
      setData(dataAll);
    }
  };

  const onModalOpen = () => {
    setSelectedDoc({});
    setAddEditModalOpen(true);
  };

  const searchPayload = {
    query: search,
    data: dataAll,
    frontendFunc: setData,
    apiFunc: searchDocs,
  };

  const columnsPayload = {
    data,
    deleteDoc,
    entityNameSingular,
    handleModalOpen,
    setData,
    setSelectedDoc,
    updateDoc,
  };
  const columns = LanguagesCol(columnsPayload, t);

  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Breadcrumb links={breadcrumbList}/>
        <PageCardHeader
          onClick={onModalOpen}
          onSearch={() => onSearch(searchPayload)}
          onSearchTextChange={(event) => onSearchTextChange(event)}
          searchProps={true}
          searchSpinnerArea={searchSpinnerArea}
          searchText={search}
          showCreateModal={true}
          title={t("languages.label_many")}
        />
        <SpinnerWithPromise area="user-actions"/>
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <CreateEditModalConfigLanguages
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={data}
        setData={setData}
        language={selectedDoc}
        lng={lng}
      />
    </Layout>
  );
}
