// Function to receive the key of config operator subscription_daily/weekly/montly_* and return the name with replaced _ and uppercase first letter
// For UI purpose
export const subscriptionPlansRename = (name: string): string => {
  return capitalizeFirstLetter(name?.replace(/subscription_daily_|subscription_weekly_|subscription_monthly_/gi, "")?.replace("_", " "));
};

export const capitalizeFirstLetter = (string: string): string => string.charAt(0).toUpperCase() + string.slice(1);

export const capitalizeFirstLetterOfEachWord = (string: string): string => {
  const arr = string.split(" ");
  for (let i = 0; i < arr.length; i++) {
    arr[i] = arr[i].charAt(0).toUpperCase() + arr[i].slice(1);
  }
  return arr.join(" ");
};

export const ymdToDmy = (string: string): string => {
  const p = string.split(/\D/g);
  return [p[2], p[1], p[0]].join("/");
};
