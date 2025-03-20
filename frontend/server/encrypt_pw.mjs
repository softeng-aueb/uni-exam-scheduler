import bcrypt from "bcrypt";

async function hashPassword(password) {
  const salt = await bcrypt.genSalt(10);
  return await bcrypt.hash(password, salt);
}

function validatePassword(hashedPassword, inputPassword) {
  return bcrypt.compareSync(inputPassword, hashedPassword);
}

if (process.argv.length === 2) {
    console.error('Expected at least one argument!');
    process.exit(1);
}

let password = process.argv[2]
console.log(password)

let hash = await hashPassword(password)

console.log(hash)
console.log(validatePassword(hash, password))



  


