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
import CheckboxComponent from "../../Form/CheckBoxComponent";
import SelectComponent from "@/app/ui/Form/SelectComponent";
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { createListing, updateListing } from "@/app/lib/dbOperations/collections/Listings";
import { onAction } from "../../Common/cruds";
import { useTranslation } from "@/app/i18n/client";

export default function CreateEditModalListings(props: any) {
  const { open, handleClose, listing, listingCategories, data, setData, lng } = props;
  const { t } = useTranslation(lng);

  const initialValues = {
    active: listing?.active || false,
    status: listing?.status || "INACTIVE",
    order: listing?.order || "",
    /* */
    name: listing?.name || "",
    listing_category_id: listing?.listing_category_id || "",
    listing_category_name: listing?.listing_category_name || "",
  };

  const validationSchema = yup.object({
    order: yup.string().trim().required("order is required"),
    active: yup.boolean(),
    /* */
    name: yup.string().trim().required("name name is required"),
  });

  const spinnerArea = listing?._id ? "update-listing" : "create-listing";
  const headerTitle = listing?._id ? "Update Listing" : "Create Listing";

  const handleContinue = async (values: any) => {
    const payload = {
      frontendFunc: setData,
      data,
    };
    try {
      const { data: resp }: any = listing?._id ? await updateListing(listing._id, values) : await createListing(values);
      if (resp?.insertedId) {
        values._id = resp?.insertedId;
      } else {
        values._id = listing._id;
      }
      onAction(values, payload);
      handleClose();
    } catch (e: any) {
      console.log(e);
    }
  };

  const handleListingCategoryChange = (e: any, setFieldValue: any) => {
    const val = e.target.value; // val is of the form `${el._id}---${el.name}`
    const id = val.split("---")[0];
    const name = val.split("---")[1];
    // setFieldValue("listing_category_id", Array.isArray(val) ? val.sort() : val);
    setFieldValue("listing_category_id", id);
    setFieldValue("listing_category_name", name);
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
                    placeHolder={t("listings.field.name")}
                    value={values.name}
                    handleTextChange={handleChange("name")}
                    hasError={!!errors.name}
                    errorMessage={errors.name as string}
                    id="name"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("listings.field.order")}
                    value={values.order}
                    handleTextChange={handleChange("order")}
                    hasError={!!errors.order}
                    errorMessage={errors.order as string}
                    id="order"
                  />
                </Grid>
                <Grid item xs={12} md={12}>
                  <SelectComponent
                    menuItems={listingCategories?.map((el: any) => {
                      return {
                        label: el.name,
                        value: `${el._id}---${el.name}`,
                      };
                    })}
                    // value={values.listing_category_id}
                    value={`${values.listing_category_id}---${values.listing_category_name}`}
                    onSelect={(e: any) => handleListingCategoryChange(e, setFieldValue)}
                    hasError={!!errors.roles}
                    errorMessage={errors.roles as string}
                    placeHolder={"Select Listing Category"}
                    id="roles"
                    // multiple
                  />
                </Grid>
                <Grid item xs={12} md={12}>
                  <CheckboxComponent
                    checked={values.active}
                    handleChange={(e: any) => setFieldValue("active", e.target.checked)}
                    label="Active"
                    id="active_listing"
                  />
                </Grid>
              </Grid>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <UserActionButton
                    label={listing?._id ? t("listings.update_btn") : t("listings.create_btn")}
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
