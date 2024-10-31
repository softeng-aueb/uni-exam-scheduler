import * as React from "react";
import { usePromiseTracker } from "react-promise-tracker";
import Spinner from "./index";

type Props = {
  area: string;
};
export default function SpinnerWithPromise(props: Props): JSX.Element | null {
  const { promiseInProgress } = usePromiseTracker({ area: props.area });

  return promiseInProgress ? <Spinner/> : null;
}
