import React, { ChangeEvent, KeyboardEvent } from "react";

import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Tags from "./Tags";

type Props = {
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  onDelete: Function;
  onKeyDown: (e: KeyboardEvent) => void;
  tags: Array<string>;
  value: string;
}

export default function InputTags(props: Props) {
  const { onChange, onDelete, onKeyDown, tags, value } = props;

  return (
    <Box sx={{ flexGrow: 1 }}>

      <TextField
        fullWidth
        variant='standard'
        size='small'
        value={value}
        sx={{ margin: "1rem 0" }}
        onChange={onChange}
        onKeyDown={onKeyDown}
        placeholder={"Enter tags"}
        InputProps={{
          startAdornment: (
            <Box sx={{ display: "flex", gap: 1, marginBottom: "0.5rem" }}>
              {tags.map((data, index) => {
                return (
                  <Tags data={data} handleDelete={onDelete} key={index}/>
                );
              })}
            </Box>
          ),
        }}
      />
    </Box>
  );
}
