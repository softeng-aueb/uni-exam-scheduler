import * as React from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";

// Components
import { TextInput } from "../../Form/InputComponent";
import CreateEditModalCommon from "../Common/ModalStyles";
import { UserActionButton } from "../../Buttons/modalButtons";

// Services
import { createListingCategory, updateListingCategory } from "@/app/lib/dbOperations/collections/ListingCategories";
import { onAction } from "../../Common/cruds";
import CheckboxComponent from "../../Form/CheckBoxComponent";
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { useTranslation } from "@/app/i18n/client";

export default function CreateEditModalListingCategories(props: any) {
  const { open, handleClose, listingCategory, data, setData, lng } = props;
  const { t } = useTranslation(lng);

  const initialValues = {
    active: listingCategory?.active || false,
    status: listingCategory?.status || "INACTIVE",
    order: listingCategory?.order || "",
    /* */
    name: listingCategory?.name || "",
  };

  const validationSchema = yup.object({
    name: yup.string().trim().required("name name is required"),
    order: yup.string().trim().required("order is required"),
    active: yup.boolean(),
  });

  const spinnerArea = listingCategory?._id ? "update-listingCategory" : "create-listingCategory";
  const headerTitle = listingCategory?._id ? "Update ListingCategory" : "Create ListingCategory";

  const handleContinue = async (values: any) => {
    const payload = {
      frontendFunc: setData,
      data,
    };
    try {
      const { data: resp }: any = listingCategory?._id ? await updateListingCategory(listingCategory._id, values) : await createListingCategory(values);
      if (resp?.insertedId) {
        values._id = resp?.insertedId;
      } else {
        values._id = listingCategory._id;
      }
      onAction(values, payload);
      handleClose();
    } catch (e: any) {
      console.log(e);
    }
  };
  return (
    <CreateEditModalCommon open={open} handleClose={handleClose} headerTitle={headerTitle}>
      <Formik
        enableReinitialize={true}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={async (values: any) => await handleContinue(values)}
      >
        {({ values, handleSubmit, errors, setFieldValue, handleChange, dirty }) => {
          return (
            <Form>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("listingCategories.field.name")}
                    value={values.name}
                    handleTextChange={handleChange("name")}
                    hasError={!!errors.name}
                    errorMessage={errors.name as string}
                    id="name"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("listingCategories.field.order")}
                    value={values.order}
                    handleTextChange={handleChange("order")}
                    hasError={!!errors.order}
                    errorMessage={errors.order as string}
                    id="order"
                  />
                </Grid>
                <Grid item xs={12} md={12}>
                  <CheckboxComponent
                    checked={values.active}
                    handleChange={(e: any) => setFieldValue("active", e.target.checked)}
                    label="Active"
                    id="active_listingCategory"
                  />
                </Grid>
              </Grid>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <UserActionButton
                    label={listingCategory?._id ? t("listingCategories.update_btn") : t("listingCategories.create_btn")}
                    type="submit"
                    handleClick={handleSubmit}
                    disabled={false}
                  />
                </Grid>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <SpinnerWithPromise area={spinnerArea}/>
                </Grid>
              </Grid>
            </Form>
          );
        }}
      </Formik>
    </CreateEditModalCommon>

  );
}
