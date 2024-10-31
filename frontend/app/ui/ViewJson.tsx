import React from "react";
import { default as ReactJson } from "react-json-view";

import BrowserOnly from "./BrowserOnly";

interface Props {
  data: Object | null;
  collapsed: boolean;
  onEdit?: any;
  onAdd?: any;
  onDelete?: any;
}

export default function ViewJson({ data, collapsed, onEdit, onAdd, onDelete }: Props) {
  return data ? (
    <div
      style={{
        fontSize: 12,
        height: "50vh",
        minHeight: "10vh",
        overflow: "auto",
        padding: 25,
        width: "100%",
      }}
    >
      <BrowserOnly>
        {() => {
          const ReactJson = require("react-json-view").default;
          return <ReactJson
            src={data}
            onEdit={onEdit}
            onAdd={onAdd}
            onDelete={onDelete}
            collapsed={collapsed}
            displayDataTypes={false}
            enableClipboard={false}
            name={false}
            quotesOnKeys={false}
          />;
        }}
      </BrowserOnly>
    </div>
  ) : null;
}
