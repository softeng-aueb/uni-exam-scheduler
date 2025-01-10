import * as React from "react";

// MUI-RTE
import MUIRichTextEditor from "mui-rte";

// MUI
import { createTheme, ThemeProvider } from "@mui/material/styles";

const defaultTheme = createTheme();

Object.assign(defaultTheme, {
  overrides: {
    MUIRichTextEditor: {
      root: {
        width: "100%",
        border: "1px solid #ccc",
      },
      editor: {
        height: "10rem",
        borderTop: "1px solid #ccc",
        overflowY: "auto",
        padding: "0.5rem",
      },
    },
  },
});

export default function TextAreaWithRTE({ onChange, value }) {
  return (
    <ThemeProvider theme={defaultTheme}>
      <MUIRichTextEditor
        controls={[
          "bold",
          "italic",
          "underline",
          "bulletList",
          "numberList",
          "link",
          "custom-tag",
        ]}
        inlineToolbar={true}
        value={value}
        onChange={onChange}
      />
    </ThemeProvider>
  );
}
