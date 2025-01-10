// const configGoogle = require("eslint-config-google");

module.exports = [
  {
    // "extends":       [
    //   "next/core-web-vitals",
    //   "google",
    // ],
    "languageOptions": {
      "ecmaVersion": 2024,
    },
    // "env":           {
    //   "es6": true,
    // },
    "rules": {
      // The rules below are listed in the order they appear on the eslint
      // rules page. All rules are listed to make it easier to keep in sync
      // as new ESLint rules are added.
      // http://eslint.org/docs/rules/
      // - Rules in the `eslint:recommended` ruleset that aren't specifically
      //   mentioned by the google styleguide are listed but commented out (so
      //   they don't override a base ruleset).
      // - Rules that are recommended but contradict the Google styleguide
      //   are explicitely set to the Google styleguide value.

      // Possible Errors
      // http://eslint.org/docs/rules/#possible-errors
      // ---------------------------------------------
      // 'for-direction': 0,
      // 'no-await-in-loop': 0,
      // 'no-compare-neg-zero': 2, // eslint:recommended
      // 'no-console': 2, // eslint:recommended
      // 'no-constant-condition': 2, // eslint:recommended
      // 'no-control-regex': 2, // eslint:recommended
      // 'no-debugger': 2, // eslint:recommended
      // 'no-dupe-args': 2, // eslint:recommended
      // 'no-dupe-keys': 2, // eslint:recommended
      // 'no-duplicate-case': 2, // eslint:recommended
      // 'no-empty': 2, // eslint:recommended
      // 'no-empty-character-class': 2, // eslint:recommended
      // 'no-ex-assign': 2, // eslint:recommended
      // 'no-extra-boolean-cast': 2, // eslint:recommended
      // 'no-extra-parens': 0,
      // 'no-extra-semi': 2, // eslint:recommended
      // 'no-func-assign': 2, // eslint:recommended
      // 'no-inner-declarations': 2, // eslint:recommended
      // 'no-invalid-regexp': 2, // eslint:recommended
      // 'no-obj-calls': 2, // eslint:recommended
      // 'no-prototype-builtins': 0,
      // 'no-regex-spaces': 2, // eslint:recommended
      // 'no-sparse-arrays': 2, // eslint:recommended
      // 'no-template-curly-in-string': 0,
      // 'no-unreachable': 2, // eslint:recommended
      // 'no-unsafe-finally': 2, // eslint:recommended
      // 'no-unsafe-negation': 0,
      // 'use-isnan': 2, // eslint:recommended
      // 'valid-typeof': 2, // eslint:recommended
      // // 'valid-jsdoc': [2, {
      // //   requireParamDescription: false,
      // //   requireReturnDescription: false,
      // //   requireReturn: false,
      // //   prefer: {returns: 'return'},
      // // }],
      "no-cond-assign": 0, // eslint:recommended
      "no-irregular-whitespace": 2, // eslint:recommended
      "no-unexpected-multiline": 2, // eslint:recommended

      // Best Practices
      // http://eslint.org/docs/rules/#best-practices
      // --------------------------------------------

      // 'accessor-pairs': 0,
      // 'array-callback-return': 0,
      // 'block-scoped-var': 0,
      // 'class-methods-use-this': 0,
      // 'complexity': 0,
      // 'consistent-return': 0,
      // 'default-case': 0,
      // 'dot-location': 0,
      // 'dot-notation': 0,
      // 'eqeqeq': 0,
      // 'no-alert': 0,
      // 'no-case-declarations': 2, // eslint:recommended
      // 'no-div-regex': 0,
      // 'no-else-return': 0,
      // 'no-empty-function': 0,
      // 'no-empty-pattern': 2, // eslint:recommended
      // 'no-eq-null': 0,
      // 'no-eval': 0,
      // 'no-extra-label': 0,
      // 'no-fallthrough': 2, // eslint:recommended
      // 'no-floating-decimal': 0,
      // 'no-global-assign': 0,
      // 'no-implicit-coercion': 0,
      // 'no-implicit-globals': 0,
      // 'no-implied-eval': 0,
      // 'no-iterator': 0,
      // 'no-labels': 0,
      // 'no-lone-blocks': 0,
      // 'no-loop-func': 0,
      // 'no-magic-numbers': 0,
      // 'no-new': 0,
      // 'no-new-func': 0,
      // 'no-octal': 2, // eslint:recommended
      // 'no-octal-escape': 0,
      // 'no-param-reassign': 0,
      // 'no-proto': 0,
      // 'no-redeclare': 2, // eslint:recommended
      // 'no-restricted-properties': 0,
      // 'no-return-assign': 0,
      // 'no-script-url': 0,
      // 'no-self-assign': 2, // eslint:recommended
      // 'no-self-compare': 0,
      // 'no-sequences': 0,
      // 'no-unmodified-loop-condition': 0,
      // 'no-unused-expressions': 0,
      // 'no-unused-labels': 2, // eslint:recommended
      // 'no-useless-call': 0,
      // 'no-useless-concat': 0,
      // 'no-useless-escape': 0,
      // 'no-void': 0,
      // 'no-warning-comments': 0,
      // 'radix': 0,
      // 'require-await': 0,
      // 'vars-on-top': 0,
      // 'wrap-iife': 0,
      // 'yoda': 0,
      // // 'no-invalid-this': 2,
      "curly": [2, "multi-line"],
      "guard-for-in": 2,
      "no-caller": 2,
      "no-extend-native": 2,
      "no-extra-bind": 2,
      "no-multi-spaces": 2,
      "no-multi-str": 2,
      "no-new-wrappers": 2,
      "no-throw-literal": 2, // eslint:recommended
      "no-with": 2,
      "prefer-promise-reject-errors": 2,

      // Strict Mode
      // http://eslint.org/docs/rules/#strict-mode
      // -----------------------------------------
      // 'strict': 0,

      // Variables
      // http://eslint.org/docs/rules/#variables
      // ---------------------------------------
      // 'init-declarations': 0,
      // 'no-catch-shadow': 0,
      // 'no-delete-var': 2, // eslint:recommended
      // 'no-label-var': 0,
      // 'no-restricted-globals': 0,
      // 'no-shadow': 0,
      // 'no-shadow-restricted-names': 0,
      // 'no-undef': 2, // eslint:recommended
      // 'no-undef-init': 0,
      // 'no-undefined': 0,
      // 'no-use-before-define': 0,
      // // 'no-unused-vars': [2, {args: 'none'}], // eslint:recommended

      // Node.js and CommonJS
      // http://eslint.org/docs/rules/#nodejs-and-commonjs
      // -------------------------------------------------
      // 'callback-return': 0,
      // 'global-require': 0,
      // 'handle-callback-err': 0,
      // 'no-buffer-constructor': 0,
      // 'no-mixed-requires': 0,
      // 'no-new-require': 0,
      // 'no-path-concat': 0,
      // 'no-process-env': 0,
      // 'no-process-exit': 0,
      // 'no-restricted-modules': 0,
      // 'no-sync': 0,

      // Stylistic Issues
      // http://eslint.org/docs/rules/#stylistic-issues
      // ----------------------------------------------
      // 'capitalized-comments': 0,
      // 'consistent-this': 0,
      // 'func-name-matching': 0,
      // 'func-names': 0,
      // 'func-style': 0,
      // 'id-blacklist': 0,
      // 'id-length': 0,
      // 'id-match': 0,
      // 'jsx-quotes': 0,
      // 'line-comment-position': 0,
      // 'lines-around-comment': 0,
      // 'max-lines': 0,
      // 'max-nested-callbacks': 0,
      // 'max-params': 0,
      // 'max-statements': 0,
      // 'max-statements-per-line': 0,
      // 'multiline-ternary': 0,
      // 'new-parens': 0,
      // 'newline-per-chained-call': 0,
      // 'no-bitwise': 0,
      // 'no-continue': 0,
      // 'no-inline-comments': 0,
      // 'no-lonely-if': 0,
      // 'no-mixed-operators': 0,
      // 'no-multi-assign': 0,
      // 'no-negated-condition': 0,
      // 'no-nested-ternary': 0,
      // 'no-plusplus': 0,
      // 'no-restricted-syntax': 0,
      // 'no-ternary': 0,
      // 'no-underscore-dangle': 0,
      // 'no-unneeded-ternary': 0,
      // 'no-whitespace-before-property': 0,
      // 'nonblock-statement-body-position': 0,
      // 'object-curly-newline': 0,
      // 'object-property-newline': 0,
      // 'one-var-declaration-per-line': 0,
      // 'operator-assignment': 0,
      // 'padding-line-between-statements': 0,
      // 'semi-style': 0,
      // 'sort-keys': 0,
      // 'sort-vars': 0,
      // 'space-in-parens': 0,
      // 'space-infix-ops': 0,
      // 'space-unary-ops': 0,
      // 'template-tag-spacing': 0,
      // 'unicode-bom': 0,
      // 'wrap-regex': 0,
      // // 'camelcase': [2, {properties: 'never'}],
      // // 'linebreak-style': 2,
      // // 'new-cap': 2,
      // // 'object-curly-spacing': 2,
      // // 'quotes': [2, 'single', {allowTemplateLiterals: true}],

      // 'max-depth': 0,
      // // 'max-len': [2, {
      // //   code: 80,
      // //   tabWidth: 2,
      // //   ignoreUrls: true,
      // //   ignorePattern: 'goog\.(module|require)',
      // // }],
      // // 'require-jsdoc': [2, {
      // //   require: {
      // //     FunctionDeclaration: true,
      // //     MethodDefinition: true,
      // //     ClassDeclaration: true,
      // //   },
      // // }],
      "array-bracket-newline": 0, // eslint:recommended
      "array-bracket-spacing": [2, "never"],
      "array-element-newline": 0, // eslint:recommended
      "block-spacing": [2, "never"],
      "brace-style": 2,
      "comma-dangle": [2, "always-multiline"],
      "comma-spacing": 2,
      "comma-style": 2,
      "computed-property-spacing": 2,
      "eol-last": 2,
      "func-call-spacing": 2,
      "indent": [
        2, 2, {
          "CallExpression": {
            "arguments": 2,
          },
          "FunctionDeclaration": {
            "body": 1,
            "parameters": 2,
          },
          "FunctionExpression": {
            "body": 1,
            "parameters": 2,
          },
          "MemberExpression": 2,
          "ObjectExpression": 1,
          "SwitchCase": 1,
          "ignoredNodes": [
            "ConditionalExpression",
          ],
        },
      ],
      "key-spacing": 2,
      "keyword-spacing": 2,
      "no-array-constructor": 2,
      "no-mixed-spaces-and-tabs": 2, // eslint:recommended
      "no-multiple-empty-lines": [2, { max: 2 }],
      "no-new-object": 2,
      "no-tabs": 2,
      "no-trailing-spaces": 2,
      "one-var": [2, { var: "never", let: "never", const: "never" }],
      "operator-linebreak": [2, "after"],
      "padded-blocks": [2, "never"],
      "quote-props": [2, "consistent"],
      "semi": 2,
      "semi-spacing": 2,
      "space-before-blocks": 2,
      "space-before-function-paren": [2, { asyncArrow: "always", anonymous: "never", named: "never" }],
      "spaced-comment": [2, "always"],
      "switch-colon-spacing": 2,

      // ECMAScript 6
      // http://eslint.org/docs/rules/#ecmascript-6
      // ------------------------------------------
      // 'arrow-body-style': 0,
      // TODO(philipwalton): technically arrow parens are optional but
      // recommended. ESLint doesn't support a *consistent* setting so
      // "always" is used.
      // 'arrow-spacing': 0,
      // 'no-class-assign': 0,
      // 'no-confusing-arrow': 0,
      // 'no-const-assign': 0, // eslint:recommended
      // 'no-dupe-class-members': 0, // eslint:recommended
      // 'no-duplicate-imports': 0,
      // 'no-restricted-imports': 0,
      // 'no-useless-computed-key': 0,
      // 'no-useless-constructor': 0,
      // 'no-useless-rename': 0,
      // 'object-shorthand': 0,
      // 'prefer-arrow-callback': 0,
      // 'prefer-destructuring': 0,
      // 'prefer-numeric-literals': 0,
      // 'prefer-template': 0,
      // 'require-yield': 2, // eslint:recommended
      // 'sort-imports': 0,
      // 'symbol-description': 0,
      // 'template-curly-spacing': 0,
      "arrow-parens": [2, "always"],
      "constructor-super": 2, // eslint:recommended
      "generator-star-spacing": [2, "after"],
      "no-new-symbol": 2, // eslint:recommended
      "no-this-before-super": 2, // eslint:recommended
      "no-var": 2,
      "prefer-const": [2, { destructuring: "all" }],
      "prefer-rest-params": 2,
      "prefer-spread": 2,
      "rest-spread-spacing": 2,
      "yield-star-spacing": [2, "after"],

      // GU rules
      "@next/next/no-page-custom-font": "off",
      "camelcase": "off",
      "linebreak-style": "off",
      "max-len": "off",
      "max-line-length": "off",
      "new-cap": "off",
      "no-invalid-this": "off",
      "no-unused-vars": "off",
      "react-hooks/exhaustive-deps": "off",
      "require-jsdoc": "off",
      "quotes": ["error", "double"],
      "object-curly-spacing": ["error", "always"],
    },
  },
];
