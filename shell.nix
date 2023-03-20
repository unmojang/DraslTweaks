with import <nixpkgs>{};

let
    pkgs = import (fetchTarball("https://github.com/NixOS/nixpkgs/archive/nixos-22.11.tar.gz")) {};

in pkgs.mkShell {
    nativeBuildInputs = with pkgs; [ jdk17_headless ];
}
