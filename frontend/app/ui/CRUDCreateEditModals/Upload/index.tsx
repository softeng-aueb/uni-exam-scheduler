import * as React from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { styled } from "@mui/material/styles";

// Components
import CreateEditModalCommon from "../Common/ModalStyles";

// Services
import { onAction } from "../../Common/cruds";

import { uploadFile } from "@/app/lib/dbOperations/collections/Upload";
import { Box } from "@mui/material";

const Input = styled('input')({
  display: 'none',
});

export default function FileUploadModal(props: any) {
  const { open, handleClose, data, setData } = props;

  const initialValues = {
    file: null,
  };

  const validationSchema = yup.object({
    file: yup.mixed().required("File is required"),
  });

  const headerTitle = "Upload File";

  const handleContinue = async (values: any) => {
    const payload = {
      frontendFunc: setData,
      data,
    };

    const formData = new FormData();
    formData.append("file", values.file);

    try {
      const { data: resp }: any =  await uploadFile(formData);
      //if (resp?.insertedId) {
      //  values._id = resp?.insertedId;
      //} else {
      //  values._id = country._id;
      //}
      //onAction(values, payload);
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
        {({ values, handleSubmit, errors, setFieldValue }) => (
          <Form>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <Typography variant="subtitle1">Choose a file to upload:</Typography>
                <label htmlFor="file-upload">
                  <Input
                    //accept="image/*,video/*,.pdf"  // Adjust as needed
                    id="file-upload"
                    type="file"
                    onChange={(event) => {
                      if (event.currentTarget.files && event.currentTarget.files[0]) {
                        setFieldValue("file", event.currentTarget.files[0]);
                      }
                    }}
                  />
                  <Box>{values?.file?.name}</Box>
                  <Button
                    variant="contained"
                    component="span"
                  >
                    Browse File
                  </Button>
                </label>
                {errors.file && typeof errors.file === 'string' && (
                  <div style={{ color: 'red' }}>{errors.file}</div>
                )}
              </Grid>
            </Grid>
            <Grid container spacing={2} marginTop={2}>
              <Grid item xs={12} sm={6} md={6} lg={6}>
                <Button
                  type="submit"
                  onClick={() => handleSubmit()}
                  disabled={false}
                  variant="contained"
                >
                  Upload
                </Button>
              </Grid>
            </Grid>
          </Form>
        )}
      </Formik>
    </CreateEditModalCommon>
  );
}
