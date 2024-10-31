"use client";

import NextLink from "next/link";
import React, { useEffect } from "react";

// import AddIcon from "@mui/icons-material/Add";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardHeader from "@mui/material/CardHeader";
import IconButton from "@mui/material/IconButton";
import InputAdornment from "@mui/material/InputAdornment";
import MenuItem from "@mui/material/MenuItem";
import Search from "@mui/icons-material/Search";
import TextField from "@mui/material/TextField";

import ReturnButton, { WarningAlertProps } from "./ReturnButton";
import SpinnerWithPromise from "./Spinner/SpinnerPromise";
import CreateButton from "./Buttons/CreateButton";

type CreateProps = {
  uri: string
}

interface PageCardHeaderProps {
  elementId?: string
  title?: any
  returnProps?: WarningAlertProps
  searchProps?: boolean
  searchText?: string
  onSearchTextChange?: (event: any) => void
  onSearch?: () => any
  onClick?: () => any
  iconButtonProps?: any
  showCreateModal?: boolean
  searchPlaceholder?: string
  searchSpinnerArea?: string;
  createProps?: CreateProps
  child?: any;
  Icon?: any;
  ddlProps?: any;
  children?: JSX.Element | JSX.Element[] | any
}

export default function PageCardHeader({
                                         elementId,
                                         title,
                                         returnProps,
                                         searchProps,
                                         searchText,
                                         onSearchTextChange,
                                         onSearch,
                                         onClick,
                                         iconButtonProps,
                                         Icon,
                                         showCreateModal,
                                         searchPlaceholder = `Search ${title}`,
                                         searchSpinnerArea,
                                         createProps,
                                         child,
                                         children,
                                         ddlProps,
                                       }: PageCardHeaderProps) {
  useEffect(() => {
  }, [ddlProps?.ddlValue]);

  const renderActions = () => {
    if (elementId === undefined) {
      elementId = title?.toLowerCase().replace(/\s+/g, "-") || "";
    }

    const returnBtn = returnProps && returnProps.uri && (
      <ReturnButton
        elementId={`return-button-${elementId}`}
        returnProps={returnProps}
      />
    );

    const renderCreateButton = () => {
      if (createProps?.uri) {
        return (
          <NextLink href={createProps.uri} passHref>
            <CreateButton elementId={elementId}/>
          </NextLink>
        );
      } else if (showCreateModal) {
        return (
          <Box sx={{ mx: 1 }}>
            <CreateButton elementId={elementId} onClick={onClick}/>
          </Box>
        );
      }
    };

    const iconButton = () => {
      if (iconButtonProps?.uri) {
        return (
          <NextLink href={iconButtonProps.uri} passHref>
            <IconButton aria-label="icon" sx={{ marginRight: "1rem" }}>
              <Icon/>
            </IconButton>
          </NextLink>

        );
      }
    };

    const searchArea = searchProps && (
      <Box sx={{ display: "flex", gap: 1 }}>
        <TextField
          id={`search-${elementId}`}
          placeholder={searchPlaceholder}
          // size="small"
          onChange={onSearchTextChange}
          value={searchText}
          variant="standard"
          InputProps={{
            endAdornment: (
              <InputAdornment position="start">
                <IconButton
                  aria-label="search"
                  onClick={onSearch}
                  onMouseDown={onSearch}
                  // edge="end"
                >
                  <Search/>
                </IconButton>
              </InputAdornment>
            ),
          }}
        />
        <SpinnerWithPromise area={searchSpinnerArea}/>
      </Box>

    );

    const renderDDL = () => {
      if (ddlProps && Object.keys(ddlProps).length) {
        return (
          <TextField
            id="ddl-select"
            select
            label={ddlProps.label}
            value={ddlProps.value}
            onChange={ddlProps.onChange}
            variant="standard"
            style={{ marginTop: "-14px", width: 140 }}
            size="small"
          >
            {ddlProps.ddlValues.map((val) => (
              <MenuItem value={val.value} key={val.value}>{val.label}</MenuItem>
            ))}
          </TextField>
        );
      }
    };
    return (
      <CardActions sx={{ padding: 0, verticalAlign: "center" }}>
        {child}
        {iconButton()}
        {searchArea}
        {renderDDL()}
        {renderCreateButton()}
        {children}
        {returnBtn}
      </CardActions>
    );
  };

  return (
    <Card sx={{ width: "100%", marginBottom: 4 }}>
      <CardHeader
        id={`card-header-${elementId}`}
        title={title}
        titleTypographyProps={{ component: "h2" }}
        action={renderActions()}
      />
    </Card>
  );
}
