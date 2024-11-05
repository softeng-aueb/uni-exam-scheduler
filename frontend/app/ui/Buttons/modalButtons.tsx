import React from "react";

// Mui
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import { Box, Button } from "@mui/material";
import { styled } from "@mui/material/styles";

// Interface
import { IUserActionButtonProps } from "../../lib/dbOperations/schemas/ComponentsInterfaces";

const AddBtn = styled(Box)({
  "backgroundColor": "#078D95",
  "fontSize": "1rem",
  "color": "#ffffff",
  "width": "1.5rem",
  "height": "1.5rem",
  "textAlign": "center",
  "verticalAlign": "middle",
  "borderRadius": "100%",
  "cursor": "pointer",
  "position": "relative",
  "&:hover": {
    backgroundColor: "#056e75",
  },
  "&:disabled": {
    backgroundColor: "#078D95",
    opacity: "0.5",
    color: "#FFFFFF",
  },
});

const RemoveBtn = styled(Box)({
  "backgroundColor": "#d71d84",
  "color": "#ffffff",
  "transition": "all 0.2s ease-in",
  "width": "1.5rem",
  "height": "1.5rem",
  "textAlign": "center",
  "verticalAlign": "middle",
  "borderRadius": "100%",
  "cursor": "pointer",
  "position": "relative",
  "&:hover": {
    backgroundColor: "#b91972",
  },
});

const UserActionBtn = styled(Button)({
  "padding": "0.5rem 2rem",
  "backgroundColor": "#078D95",
  "fontSize": "17px",
  "&:hover": {
    backgroundColor: "#056e75",
  },
  "&:disabled": {
    backgroundColor: "#078D95",
    opacity: "0.5",
    color: "#FFFFFF",
  },
});

export const AddButton = ({ handleAdd }: { handleAdd: () => void }) => {
  return (
    <AddBtn
      onClick={handleAdd}
      sx={{ marginRight: "0.5rem" }}
    >
      <AddIcon sx={{
        fontSize: "1rem",
        fontWeight: "bold",
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
      }}/>
    </AddBtn>
  );
};

export const RemoveButton = ({ handleRemove }: { handleRemove: () => void }) => {
  return (
    <RemoveBtn

      color="secondary"
      aria-label="remove"
      onClick={handleRemove}
      sx={{ marginRight: "0.5rem" }}
    ><RemoveIcon sx={{
      fontSize: "1rem",
      position: "absolute",
      top: "50%",
      left: "50%",
      transform: "translate(-50%, -50%)",
    }}/></RemoveBtn>
  );
};

export const UserActionButton = (props: IUserActionButtonProps) => {
  const { type, label, handleClick, disabled } = props;

  return (
    <UserActionBtn variant="contained" type={type} onClick={handleClick} disabled={disabled}>
      {label}
    </UserActionBtn>
  );
};
