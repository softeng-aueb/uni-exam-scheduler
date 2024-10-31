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
import CreateEditModalConfigUsers from "@/app/ui/CRUDCreateEditModals/Users";
import UsersCol from "@/app/ui/CRUDColumns/Admin/Users/Cols";

// DB Operations
import {
  deleteUser as deleteDoc,
  editUser as updateDoc,
  fetchUsers as readDocs,
  readRoles,
  searchUsers as searchDocs,
} from "@/app/lib/dbOperations";

// Helpers
import { onSearch } from "@/app/ui/Common/cruds";
import { useTranslation } from "@/app/i18n/client";

const entityNamePlural = "Users";
const entityNameSingular = "User";
const searchSpinnerArea = "search-users-area";
const spinnerArea = "read-users";

export default function UsersComponent({ lng }: any) {
  const { data: session }: any = useSession();
  const { t } = useTranslation(lng);

  const [addEditModalOpen, setAddEditModalOpen] = useState(false);
  const [data, setData] = useState<any>([]);
  const [dataAll, setDataAll] = useState<any>([]);
  const [search, setSearch] = useState("");
  const [selectedDoc, setSelectedDoc] = useState<any>({});
  const [roles, setRoles] = useState<any>([]);

  useEffect(() => {
    async function init() {
      const [
        { data: data1 },
        { data: data2 },
      ] = await Promise.all([
        readDocs(),
        readRoles(),
      ]);

      setDataAll(data1);
      setData(data1);
      setRoles(data2);
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
    label: t("users.label_many"),
    url: "/admin/users",
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
  const columns = UsersCol(columnsPayload, t);

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
          title={t("users.label_many")}
        />
        <SpinnerWithPromise area="user-actions"/>
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <CreateEditModalConfigUsers
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={data}
        setData={setData}
        user={selectedDoc}
        roles={roles}
        lng={lng}
      />
    </Layout>
  );
}
