{
    description        = "frege-language-server-dev-shell";
    inputs.nixpkgs.url = "github:NixOS/nixpkgs/release-22.05";

    outputs = { self, nixpkgs }:
    {
        devShells = nixpkgs.lib.genAttrs 
        [ 
            "x86_64-darwin" 
            "aarch64-darwin"
            "x86_64-linux" 
        ] 
        (system:
            let
                pkgs = nixpkgs.legacyPackages.${system};
            in
            {
                default = pkgs.mkShell 
                {
                    buildInputs = with pkgs; 
                    [
                        nodejs
                    ];
                };
            }
        );
    };
}
