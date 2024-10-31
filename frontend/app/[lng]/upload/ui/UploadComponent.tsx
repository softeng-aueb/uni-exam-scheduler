"use client";

import React, { useEffect, useState } from "react";
import { useSession } from "next-auth/react";

// MUI
import { Container } from "@mui/material";

// Components
import Layout from "@/app/ui/layout";
import PageCardHeader from "@/app/ui/PageCardHeader";
import { DataGridComponent } from "@/app/ui/DataGrid";
import FileUploadModal from "@/app/ui/CRUDCreateEditModals/Upload";

// DB Operations
import {
  deleteCountry as deleteDoc,
  readUploadedFiles as readDocs,
  searchCountry as searchDocs,
  updateCountry as updateDoc,
} from "@/app/lib/dbOperations";

// Helpers
import { onSearch } from "@/app/ui/Common/cruds";
import { useTranslation } from "@/app/i18n/client";
import UploadCol from "@/app/ui/CRUDColumns/Upload/Cols";

const entityNameSingular = "Upload";
const searchSpinnerArea = "search-countries-area";
const spinnerArea = "read-countries";

export default function UploadComponent({ lng }: any) {
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
  const columns = UploadCol(columnsPayload, t);

  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <PageCardHeader
          onClick={onModalOpen}
          onSearch={() => onSearch(searchPayload)}
          onSearchTextChange={(event) => onSearchTextChange(event)}
          searchProps={true}
          searchSpinnerArea={searchSpinnerArea}
          searchText={search}
          showCreateModal={true}
          title="File Uploads"
        />
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <FileUploadModal
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={data}
        setData={setData}
        country={selectedDoc}
        lng={lng}
      />
    </Layout>
  );
}
