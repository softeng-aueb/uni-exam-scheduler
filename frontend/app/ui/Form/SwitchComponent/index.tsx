import * as React from "react";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Switch from "@mui/material/Switch";

type Props = {
  checked: boolean;
  handleChange: any;
  label: string;
  placement: any;
  id?: string;
}
export default function SwitchComponent(props: Props) {
  const {
    checked,
    handleChange,
    label,
    placement,
    id,
  } = props;
  return (
    <FormGroup>
      <FormControlLabel
        control={
          <Switch
            checked={checked}
            onChange={handleChange}
            inputProps={{ "aria-label": "controlled" }}
            id={id}
          />
        }
        label={label}
        labelPlacement={placement}
      />
    </FormGroup>
  );
}
