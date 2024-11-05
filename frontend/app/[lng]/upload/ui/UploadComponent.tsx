"use client";

import React, { useState } from "react";
import { useSession } from "next-auth/react";

// MUI
import { Container } from "@mui/material";

// Components
import Layout from "@/app/ui/layout";
import PageCardHeader from "@/app/ui/PageCardHeader";
import { DataGridComponent } from "@/app/ui/DataGrid";
import FileUploadModal from "@/app/ui/CRUDCreateEditModals/Upload";

// DB Operations
import { deleteCourse as deleteDoc, updateCourse as updateDoc, } from "@/app/lib/dbOperations";

// Helpers
import { useTranslation } from "@/app/i18n/client";
import UploadCol from "@/app/ui/CRUDColumns/Upload/Cols";

const entityNameSingular = "Upload";
const searchSpinnerArea = "search-courses-area";
const spinnerArea = "read-courses";

export default function UploadComponent({ lng }: any) {
  const { data: session }: any = useSession();
  const { t } = useTranslation(lng);

  const [addEditModalOpen, setAddEditModalOpen] = useState(false);
  const [data, setData] = useState<any>([]);
  const [dataAll, setDataAll] = useState<any>([]);
  const [search, setSearch] = useState<string>("");
  const [selectedDoc, setSelectedDoc] = useState<any>({});

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
          searchProps={false}
          searchSpinnerArea={searchSpinnerArea}
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
        course={selectedDoc}
        lng={lng}
      />
    </Layout>
  );
}
