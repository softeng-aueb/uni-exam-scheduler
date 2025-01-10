import { isEmpty } from "lodash";

export default function isPropEmpty(prop: any) {
  return isEmpty(prop);
}
