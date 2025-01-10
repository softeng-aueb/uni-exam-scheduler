import * as React from "react";

// MUI
import Search from "@mui/icons-material/Search";
import { Card, CardContent, IconButton, InputAdornment, TextField } from "@mui/material";

// Components
import PageCardHeader from "../PageCardHeader";
import { WarningAlertProps } from "../ReturnButton";

export type PageHeaderProps = {
  onSearch?: () => any;
  returnBtnVisible?: boolean;
  returnProps?: WarningAlertProps;
  searchVisible?: boolean;
  pageHeading: string;
  displayAvatar?: boolean;
  avatar?: any;
}

export default function PageHeader(
  {
    onSearch,
    returnBtnVisible,
    returnProps,
    searchVisible,
    pageHeading,
    displayAvatar,
    avatar,
  }: PageHeaderProps) {
  return (
    <Card sx={{ width: "100%", marginBottom: 4 }}>
      <CardContent>
        <div>
          <div>
            {displayAvatar && <span style={{ marginRight: "1rem" }}>{avatar}</span>}
            <h2>{pageHeading}</h2>
          </div>
          <div>
            {searchVisible && <TextField
              variant="standard"
              placeholder="Search Groups"
              InputProps={{
                endAdornment: (<InputAdornment position="start"><IconButton
                  aria-label="search"
                  onClick={() => onSearch()}
                >
                  <Search/>
                </IconButton>
                </InputAdornment>),
              }}
            />}
            {returnBtnVisible && <PageCardHeader title={pageHeading} returnProps={returnProps}/>}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
