export default {
  appName: 'Authorization and Accounting System',
  loginPage: {
    fields: {
      login: 'Username',
      password: 'Password',
      email: 'Email',
      formTitle: 'Sign in',
    },
    validations: {
      loginMustPresent: 'Login must be set',
      mailMustPresent: 'Email must be set',
      mailMustBeValid: 'Email must be valid',
      passwordMustPresent: 'Password must be set',
      passwordLength: 'Password must be between 8 and 20 characters long',
      sessionExpired: 'Session expired',
      invalidCredentials: 'Invalid username or password',
    },
    registration: {
      message: 'Don\'t have an account?',
      button: 'Register',
    }
  },
  registerPage: {
    fields: {
      login: 'Username',
      password: 'Password',
      email: 'Email',
      firstName: 'First name',
      lastName: 'Last name',
      formTitle: 'Register',
      repeatPassword: 'Repeat password',
      policy: 'By clicking "Register" you accept the terms of the User Agreement, Privacy Policy, and Cookie Policy of Groups'
    },
    validations: {
      loginMustPresent: 'Login must be set',
      emailMustPresent: 'Email must be set',
      mailMustBeValid: 'Email must be valid',
      firstNameMustPresent: 'First name must be set',
      lastNameMustPresent: 'Last name must be set',
      passwordMustPresent: 'Password must be set',
      passwordMustBeTheSame: 'Passwords must be the same',
      loginMinLength: 'Login must be at least 3 characters long',
      passwordComplexity: 'Invalid password. Please meet the requirements: 8 characters minimum, 1 digit, 1 letter, 1 special character',
    },
    button: 'Register',
    successMessage: 'You have successfully registered'
  },
  buttons: {
    add: 'Add',
    modify: 'Modify',
    delete: 'Delete',
    cancel: 'Cancel',
    save: 'Save',
    ok: 'Ok',
    login: 'Login',
    logout: 'Logout',
    backButton: 'Back',
  },
  exception: {
    tooManyLoginAttempts: 'Too many login attempts. Try again in 1 minute',
    invalidCredentials: 'Invalid username or password',
    emailExists: 'Email already exists',
    usernameExists: 'Username already exists',
    authenticationFailed: 'Authentication failed',
    badCredentials: 'Authentication Failed. Username or Password not valid.',
    tokenExpired: 'Token expired'
  }
}
