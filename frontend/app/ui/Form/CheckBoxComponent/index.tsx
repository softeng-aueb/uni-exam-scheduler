import * as React from "react";

import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormGroup from "@mui/material/FormGroup";

type Props = {
  checked: boolean;
  handleChange: any;
  label: string;
  disabled?: boolean;
  id?: string;
}
export default function CheckboxComponent(props: Props) {
  const { checked, handleChange, label, disabled = false, id } = props;
  return (
    <FormGroup>
      <FormControlLabel
        control={
          <Checkbox
            checked={checked}
            onChange={handleChange}
            inputProps={{ "aria-label": "controlled" }}
            disabled={disabled}
            sx={{ "my": "3px" }}
            id={id}
          />
        }
        label={label}
      />
    </FormGroup>
  );
}
