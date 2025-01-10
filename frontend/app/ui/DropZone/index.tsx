import { Grid, Typography } from "@material-ui/core";
import Stack from "@mui/material/Stack";
import React, { useCallback, useMemo, useState } from "react";
import { FileError, FileRejection, useDropzone } from "react-dropzone";
import PublishIcon from "@mui/icons-material/Publish";

export interface UploadableFile {
  id: number;
  file: File;
  errors: FileError[];
  url?: string;
}

// Do not change this styling unless it is needed!!!
const baseStyle = {
  // height: "80px",
  alignItems: "center",
  backgroundColor: "#fafafa",
  borderColor: "#056e75",
  borderRadius: 10,
  borderStyle: "dashed",
  borderWidth: 5,
  color: "#bdbdbd",
  cursor: "pointer",
  display: "flex",
  flex: 1,
  justifyContent: "center",
  margin: 10,
  outline: "none",
  padding: 20,
  transition: "border .24s ease-in-out",
};

const focusedStyle = {
  // borderColor: "#2196f3",
};

const acceptStyle = {
  borderColor: "#00e676",
};

const rejectStyle = {
  borderColor: "#ff1744",
};
// const useStyles = makeStyles((theme) => ({
//   dropzone: {
//     border: `2px dashed ${theme.palette.primary.main}`,
//     borderRadius: theme.shape.borderRadius,
//     display: "flex",
//     alignItems: "center",
//     justifyContent: "center",
//     background: theme.palette.background.default,
//     height: theme.spacing(10),
//     outline: "none",
//   },
// }));

export function DropZone({ onChange }: { onChange: (file: File[]) => Promise<void> }) {
  // const classes = useStyles();

  const [files, setFiles] = useState<UploadableFile[] | File[]>([]);
  const onDrop = useCallback((accFiles: File[], rejFiles: FileRejection[]) => {
    const _files = [...accFiles, ...rejFiles.map((rf) => rf.file)];
    setFiles(_files);
    onChange(_files);
  }, []);

  const {
    getRootProps,
    getInputProps,
    isFocused,
    isDragAccept,
    isDragReject,
  } = useDropzone({
    onDrop,
    maxFiles: 1,
    maxSize: 300 * 1024, // 300KB
  });

  const style = useMemo(() => ({
    ...baseStyle,
    ...(isFocused ? focusedStyle : {}),
    ...(isDragAccept ? acceptStyle : {}),
    ...(isDragReject ? rejectStyle : {}),
  }), [
    isFocused,
    isDragAccept,
    isDragReject,
  ]);

  return (
    <Grid container spacing={2} alignItems="center" justifyContent="center">
      <Grid item xs={12}>
        <div {...getRootProps({ style })}>
          <input {...getInputProps()} />
          <Grid container spacing={2} alignItems="center" justifyContent="center">
            <Stack direction="column">
              <PublishIcon sx={{ marginLeft: "auto", marginRight: "auto" }}/>
              <Typography align="center" variant="button">
                Click to select a file or <br/>Drag & drop your file here
              </Typography>
            </Stack>
          </Grid>
          {/* <p>Click to select a file or <br />Drag & drop your file here</p> */}
        </div>
      </Grid>
      {files.map((fileWrapper: any) => (
        <Grid item key={fileWrapper.id}>
          {fileWrapper.file}
        </Grid>
      ))}
    </Grid>
  );
}
