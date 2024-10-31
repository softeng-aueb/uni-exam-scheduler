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
import CreateEditModalListingCategories from "@/app/ui/CRUDCreateEditModals/ListingCategories";
import ListingCategoriesCol from "@/app/ui/CRUDColumns/Admin/ListingCategories/Cols";

// DB Operations
import {
  deleteListingCategory as deleteDoc,
  readListingCategories as readDocs,
  searchListingCategory as searchDocs,
  updateListingCategory as updateDoc,
} from "@/app/lib/dbOperations";

// Helpers
import { onSearch } from "@/app/ui/Common/cruds";
import { useTranslation } from "@/app/i18n/client";

const entityNamePlural = "Listing Categories";
const entityNameSingular = "Listing Category";
const searchSpinnerArea = "search-listing-categories-area";
const spinnerArea = "read-listing-categories";

export default function ListingCategoriesComponent({ lng }: any) {
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
    label: t("listingCategories.label_many"),
    url: "/admin/listing-categories",
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
  const columns = ListingCategoriesCol(columnsPayload, t);

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
          title={t("listingCategories.label_many")}
        />
        <SpinnerWithPromise area="user-actions"/>
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <CreateEditModalListingCategories
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={data}
        setData={setData}
        listingCategory={selectedDoc}
        lng={lng}
      />
    </Layout>
  );
}
