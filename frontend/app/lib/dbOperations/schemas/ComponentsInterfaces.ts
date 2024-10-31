import React from "react";

// Types

export type IGroupingBox = {
  children?: React.ReactNode;
  info?: JSX.Element | string;
  marginBottom?: number;
  marginTop?: number;
  paddingBottom?: number;
  paddingLeft?: number;
  paddingRight?: number;
  paddingTop?: number
  theme?: "light" | "dark";
  title?: string;
}

// Interfaces

export interface IUserActionButtonProps {
  type: "button" | "submit" | "reset";
  label: string;
  handleClick: () => void;
  disabled: boolean;
}

export interface IWarningDialogProps {
  type: "FAB" | "BUTTON",
  title: string
  message: string,
  tooltip: string,
  agreeText?: string,
  disagreeText?: string,
  disabled?: boolean,
  label?: JSX.Element | string,
  onClickAgree?: any | Function | (() => Promise<any>),
  onClickDisagree?: Function,

}

export interface IDashboardCardsProps {
  icon?: any
  title?: any;
  subtitle?: string;
  subtitleLarge?: string;
  subtitleSmall?: string;
  titleLink?: any
  figures?: any
  element?: any
  barData?: any
  lineData?: any
  children?: JSX.Element
}

export interface IGridCellExpandProps {
  value: string;
  width: number;
  shouldUsePre: boolean;
}
