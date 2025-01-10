import { isEmpty } from "lodash";

import Box from "@mui/material/Box";
import Checkbox from "@mui/material/Checkbox";
import Chip from "@mui/material/Chip";
import FormControl from "@mui/material/FormControl";
import FormHelperText from "@mui/material/FormHelperText";
import InputLabel from "@mui/material/InputLabel";
import ListItemText from "@mui/material/ListItemText";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";

interface Props {
  containerStyle?: Object;
  value: any;
  onSelect: Function;
  menuItems: Array<any>;
  hasError?: boolean;
  errorMessage?: string;
  placeHolder?: string;
  size?: "medium" | "small";
  id?: string;
  onClick?: Function;
  inputRequired?: boolean
}

export default function SelectComponent(props: Props) {
  /**
   *   menu items in the following format
   * [{label:"", value:""}]
   */
  const {
    containerStyle = {},
    value,
    onSelect,
    menuItems,
    hasError = false,
    errorMessage = "",
    placeHolder = "",
    size = "medium",
    id,
    onClick,
    inputRequired = true,
  } = props;
  return (
    <Box sx={{ ...containerStyle, minWidth: 120 }}>
      <FormControl fullWidth size={size} variant="standard">
        {!isEmpty(placeHolder) && (
          <InputLabel
            htmlFor="outlined-adornment-email"
            required={inputRequired}
            error={hasError}
          >
            {placeHolder}
          </InputLabel>
        )}
        <Select
          labelId={id ? `${id}-label` : "simple-select-label"}
          id={id || "simple-select"}
          value={value}
          label={!isEmpty(placeHolder) ? placeHolder : undefined}
          onChange={(e) => onSelect(e)}
          error={hasError}
          onClick={(e: any) => onClick && onClick(e)}
          multiple
          // renderValue={(selected) => selected.join(", ")}
          renderValue={(selected) => (
            <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
              {Array.isArray(selected) && selected.map((value) => (
                <Chip size="small" key={value} label={menuItems.find((obj) => obj.value === value)?.label}/>
              ))}
            </Box>
          )}
        >
          {menuItems.length > 0 &&
            menuItems.map((menu, idx) => (
              <MenuItem value={menu.value} key={`${idx}-${menu.value}`}>
                <Checkbox checked={value?.indexOf(menu.value) > -1}/>
                <ListItemText primary={menu.label}/>
              </MenuItem>
            ))}
        </Select>
        <FormHelperText
          style={{
            display: hasError ? "block" : "none",
            color: "red",
            fontSize: 10,
          }}
          id={"component-error-email"}
        >
          {errorMessage}
        </FormHelperText>
      </FormControl>
    </Box>
  );
}
